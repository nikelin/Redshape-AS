package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.Action;
import com.vio.applications.bootstrap.BootstrapException;
import com.vio.config.IServerConfig;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.IVersionsRegistry;
import com.vio.io.protocols.core.VersionRegistryFactory;
import com.vio.io.protocols.http.HttpProtocolVersion;
import com.vio.io.protocols.http.HttpVersionsRegistry;
import com.vio.io.protocols.http.impl.HttpProtocol_11;
import com.vio.io.protocols.vanilla.VanillaProtocolVersion;
import com.vio.io.protocols.vanilla.VanillaVersionsRegistry;
import com.vio.io.protocols.vanilla.impl.VanillaProtocol_10;
import com.vio.server.*;
import com.vio.server.ServerFactoryFacade;
import com.vio.server.policy.IPolicy;
import com.vio.server.policy.PoliciesFactory;
import com.vio.server.policy.PolicyType;
import com.vio.utils.Constants;
import com.vio.utils.Registry;

import org.apache.log4j.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:45:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServersInit extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( ServersInit.class );

    public ServersInit() {
        this.setId(Action.SERVERS_ID);
        this.addDependency( Action.DATABASE_ID);
        this.markCritical();
    }

    public void process() throws BootstrapException {
        try {
            IServerConfig config = Registry.getServerConfig();

            this.initProtocols();

            log.info(" Current servers to be initialized: " + config.getServersList() );
            for ( final String serverName : config.getServersList() ) {
                Thread thread = new Thread( serverName + " server initializing thread") {
                    @Override
                    public void run() {
                        try {
                            ServersInit.this.initServerInstance(serverName);
                        } catch ( Throwable e ) {
                            log.error( e.getMessage(), e );
                        }
                    }
                };

                thread.start();
            }
        } catch ( Throwable e ) {
            throw new BootstrapException();
        }
    }

    private void initServerInstance( String serverName ) throws Throwable {
        final IServerConfig config = Registry.getServerConfig();

        String serverClass = config.getServerClass(serverName);
        if ( serverClass == null || serverClass.isEmpty() ) {
            return;
        }

        Class<? extends IServer> clazz;
        try {
            clazz = (Class<? extends IServer>) Class.forName( serverClass );
        } catch ( Throwable e ) {
            log.error( e.getMessage(),e );
            throw new Exception( serverClass + " server type for " + serverName + " is not supported by current version of the system.");
        }

        int port = config.getServerPort( serverName );
        String host = config.getServerHost( serverName );
        boolean isSSLEnabled = config.isSSLEnabled( serverName );
        if ( isSSLEnabled ) {
            port = config.getSSLServerPort( serverName );
            String sslHost = config.getSSLServerHost( serverName );
            if ( sslHost == null || sslHost.isEmpty() ) {
                sslHost = host;
            }

            host = sslHost;
        }
        log.info( "Does SSL enabled? " + ( isSSLEnabled ? "yes" : "no" ) );

        final Integer reconnectionDelay = config.getReconnectionDelay( serverName );
        final Integer reconnectionLimit = config.getReconnectionLimit( serverName );
        final Boolean isReconnectEnabled = config.isReconnectEnabled( serverName );

        int times = 0;

        try {
            IServerFactory factory = ServerFactoryFacade.createFactory(clazz);

            IProtocol protocolImpl = null;
            if ( ISocketServer.class.isAssignableFrom( clazz ) ) {
                protocolImpl = this.getProtocolImpl( serverName );
                if ( protocolImpl == null ) {
                    throw new InstantiationException();
                }

                ( (ISocketServerFactory) factory).setProtocol( protocolImpl );
            }

            IServer server = factory.newInstance( clazz, host, port, isSSLEnabled );
            for ( String policyClass : config.getServerPolicies( serverName ) )  {
                Class<? extends IPolicy> policyClazz = (Class<? extends IPolicy>) Class.forName(policyClass);
                log.info("Policy protocol class: " + config.getServerPolicyProtocol(serverName, policyClazz ) );
                Class<? extends IProtocol> policyProtocolClazz = (Class<? extends IProtocol>) Class.forName( config.getServerPolicyProtocol(serverName, policyClazz ) );

                if ( protocolImpl == null 
                        || !protocolImpl.getProtocolVersion().equals( config.getServerPolicyProtocolVersion( serverName, policyClazz ) )
                            || !policyProtocolClazz.isAssignableFrom( protocolImpl.getClass() ) ) {
                    continue;
                }

                factory.addPolicy(
                    policyProtocolClazz,
                    PolicyType.valueOf( config.getServerPolicyType( serverName, policyClass ) ),
                    PoliciesFactory.getDefault().createPolicy( policyClazz, server )
                );
            }

            boolean success = false;
            do {
                try {
                    log.info("Starting " + serverName + " server...");
                    server.startListen();
                    log.info("Server " + serverName + " has been successfully started!");
                    success = true;
                } catch ( Throwable e ) {
                    times++;

                    try {
                        Thread.sleep( reconnectionDelay * Constants.TIME_SECOND );
                    } catch ( InterruptedException ie ) {}
                }
            } while( !success && isReconnectEnabled && times < reconnectionLimit );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }
    }

    protected IProtocol getProtocolImpl( String serverName ) throws InstantiationException {
        try {
            String protocolProviderClassName = Registry.getServerConfig().getServerProtocolProvider( serverName );
            if ( protocolProviderClassName == null ) {
                throw new InstantiationException();
            }

            log.info("Protocol Provider: " + protocolProviderClassName );

            Class<? extends IVersionsRegistry> protocolProviderClass = (Class<? extends IVersionsRegistry>) Class.forName( protocolProviderClassName );
            log.info("Provider class: " + protocolProviderClass.getCanonicalName() );

            IVersionsRegistry protocolProvider = VersionRegistryFactory.getInstance(protocolProviderClass);
            log.info("Versions registry class: " + protocolProvider.getClass().getCanonicalName() );
            if ( protocolProvider == null ) {
                throw new InstantiationException();
            }

            log.info("Version: " + Registry.getServerConfig().getServerProtocolVersion( serverName ) );

            IProtocol protocol = protocolProvider.getByVersion( Registry.getServerConfig().getServerProtocolVersion( serverName ) );
            if ( protocol == null ) {
                throw new InstantiationException();
            }
            
            log.info("Protocol class:" + protocol.getClass().getCanonicalName() );
            return protocol;
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            throw new InstantiationException();
        }
    }

    private void initProtocols() throws InstantiationException {
        log.info("Initialization");
        VersionRegistryFactory.getInstance(VanillaVersionsRegistry.class)
                  .addVersion(VanillaProtocolVersion.VERSION_1, new VanillaProtocol_10() );

        VersionRegistryFactory.getInstance(HttpVersionsRegistry.class)
                  .addVersion(HttpProtocolVersion.HTTP_11, new HttpProtocol_11() );        
    }

}
