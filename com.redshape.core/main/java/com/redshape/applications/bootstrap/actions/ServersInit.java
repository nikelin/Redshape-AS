package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;
import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.config.IConfig;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.IVersionsRegistry;
import com.redshape.io.protocols.core.VersionRegistryFactory;
import com.redshape.io.protocols.http.HttpProtocolVersion;
import com.redshape.io.protocols.http.HttpVersionsRegistry;
import com.redshape.io.protocols.http.impl.HttpProtocol_11;
import com.redshape.io.protocols.vanilla.VanillaProtocolVersion;
import com.redshape.io.protocols.vanilla.VanillaVersionsRegistry;
import com.redshape.io.protocols.vanilla.impl.VanillaProtocol_10;
import com.redshape.server.*;
import com.redshape.server.ServerFactoryFacade;
import com.redshape.server.policy.IPolicy;
import com.redshape.server.policy.PoliciesFactory;
import com.redshape.server.policy.PolicyType;
import com.redshape.utils.Registry;

import org.apache.log4j.*;

import java.util.Arrays;

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

    @Override
    public void process() throws BootstrapException {
        try {
            this.initProtocols();

            log.info(" Current servers to be initialized: " + Arrays.asList( Registry.getConfig().get("servers").names() ) );
            for ( final IConfig serverConfigNode : Registry.getConfig().get("servers").childs() ) {
                Thread thread = new Thread( serverConfigNode.name() + " server initializing thread") {
                    @Override
                    public void run() {
                        try {
                            ServersInit.this.initServerInstance( serverConfigNode );
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

    private void initServerInstance( IConfig serverConfigNode ) throws Throwable {
        String serverClass = serverConfigNode.get("class").value();
        if ( serverClass == null || serverClass.isEmpty() ) {
            return;
        }

        Class<? extends IServer> clazz;
        try {
            clazz = (Class<? extends IServer>) Class.forName( serverClass );
        } catch ( Throwable e ) {
            log.error( e.getMessage(),e );
            throw new Exception( serverClass + " server type for " + serverConfigNode.name() + " is not supported by current version of the system.");
        }

        int port = Integer.valueOf( serverConfigNode.get("port").value() );
        String host = serverConfigNode.get("host").value();
        boolean isSSLEnabled = Boolean.valueOf( serverConfigNode.get("ssl").get("enabled").value() );
        if ( isSSLEnabled ) {
            port = Integer.valueOf( serverConfigNode.get("ssl").get("port").value() );
            String sslHost = serverConfigNode.get("ssl").get("host").value();
            if ( sslHost == null || sslHost.isEmpty() ) {
                sslHost = host;
            }

            host = sslHost;
        }
        log.info( "Does SSL enabled? " + ( isSSLEnabled ? "yes" : "no" ) );

        try {
            IServerFactory factory = ServerFactoryFacade.createFactory(clazz);

            IProtocol protocolImpl = null;
            if ( ISocketServer.class.isAssignableFrom( clazz ) ) {
                protocolImpl = this.getProtocolImpl( serverConfigNode );
                if ( protocolImpl == null ) {
                    throw new InstantiationException();
                }

                ( (ISocketServerFactory) factory).setProtocol( protocolImpl );
            }

            log.info("Protocol applied to " + factory.getClass().getCanonicalName() + " factory is: " + protocolImpl.getClass().getCanonicalName() );

            IServer server = factory.newInstance( clazz, host, port, isSSLEnabled );
            for ( IConfig policyConfigNode : serverConfigNode.get("policies").childs() )  {
                Class<? extends IPolicy> policyClazz = (Class<? extends IPolicy>) Class.forName(policyConfigNode.get("policyClass").value() );
                Class<? extends IProtocol> policyProtocolClazz = (Class<? extends IProtocol>) Class.forName( policyConfigNode.get("protocolClass").value() );

                if ( protocolImpl == null 
                        || !protocolImpl.getProtocolVersion().equals( policyConfigNode.get("protocolVersion") )
                            || !policyProtocolClazz.isAssignableFrom( protocolImpl.getClass() ) ) {
                    continue;
                }

                factory.addPolicy(
                    policyProtocolClazz,
                    PolicyType.valueOf( policyConfigNode.get("policyType").value() ),
                    PoliciesFactory.getDefault().createPolicy( policyClazz, server )
                );
            }

            log.info("Starting " + serverConfigNode.name() + " server...");
            server.startListen();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException();
        }
    }

    protected IProtocol getProtocolImpl( IConfig serverConfigNode ) throws InstantiationException {
        try {
            String protocolProviderClassName = serverConfigNode.get("protocol").get("providerClass").value();
            if ( protocolProviderClassName == null ) {
                throw new InstantiationException();
            }

            Class<? extends IVersionsRegistry> protocolProviderClass = (Class<? extends IVersionsRegistry>) Class.forName( protocolProviderClassName );
            
            IVersionsRegistry protocolProvider = VersionRegistryFactory.getInstance(protocolProviderClass);
            if ( protocolProvider == null ) {
                throw new InstantiationException();
            }

            IProtocol protocol = protocolProvider.getByVersion( serverConfigNode.get("protocol").get("version").value() );
            if ( protocol == null ) {
                throw new InstantiationException();
            }
//
//            Class<? extends IClientsProcessor> processorClass = (Class<? extends IClientsProcessor>)Class.forName( config.getServerProtocolClientsProcessor( serverName, protocol.getClass() ) );
//            protocol.setClientsProcessor( processorClass.newInstance() );
//            protocol.setRequestsProcessor( Class.forName( config.getServerProtocolRequestsProcessor( serverName, protocol.getClass() ) ) );
//
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
