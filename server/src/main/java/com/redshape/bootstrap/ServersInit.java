package com.redshape.bootstrap;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;

import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.IVersionsRegistry;
import com.redshape.io.protocols.core.VersionRegistryFactory;

import com.redshape.io.protocols.http.HttpProtocolVersion;
import com.redshape.io.protocols.http.HttpVersionsRegistry;
import com.redshape.io.protocols.http.impl.HttpProtocol_11;
import com.redshape.io.server.policy.IPolicy;
import com.redshape.io.server.policy.PoliciesFactory;
import com.redshape.io.server.policy.PolicyType;
import com.redshape.utils.config.IConfig;

import com.redshape.io.protocols.vanilla.VanillaProtocolVersion;
import com.redshape.io.protocols.vanilla.VanillaVersionsRegistry;
import com.redshape.io.protocols.vanilla.impl.VanillaProtocol_10;

import com.redshape.io.server.IServer;
import com.redshape.server.*;

import org.apache.log4j.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:45:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
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

            IConfig serverConfigNodes = this.getConfig().get("software").get("instances");
            log.info(" Current software to be initialized: " + Arrays.asList( serverConfigNodes.names() ) );
            for ( final IConfig serverConfigNode : serverConfigNodes.childs() ) {
                if ( serverConfigNode.get("status").value().equals("on") ) {
                    new Thread() {
                        public void run() {
                            try {
                                log.info("Starting instance of server: " + serverConfigNode.name() );
                                ServersInit.this.initServerInstance( serverConfigNode );
                            } catch ( Throwable e ) {
                                log.error( e.getMessage(), e );
                            }
                        }
                    }.start();
                } else {
                    log.info("Server " + serverConfigNode.name() + " is turned off.");     
                }
            }
        } catch ( Throwable e ) {
        	log.error( e.getMessage(), e );
            throw new BootstrapException( e.getMessage(), e );
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

        IConfig sslConfigNode = serverConfigNode.get("ssl");
        boolean isSSLEnabled = !sslConfigNode.isNull() ? Boolean.parseBoolean( serverConfigNode.get("ssl").get("enabled").value() ) : false;
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
                log.info("Protocol applied to " + factory.getClass().getCanonicalName() + " factory is: " + protocolImpl.getClass().getCanonicalName() );
            }

            IServer server = factory.newInstance( clazz, host, port, isSSLEnabled );
            if ( serverConfigNode.get("policies").hasChilds() ) {
                log.info( serverConfigNode.get("policies").childs().length + " policies applied to this server instance." );
            } else {
                log.info("There is no policies applicable to this server instance");    
            }

            for ( IConfig policyConfigNode : serverConfigNode.get("policies").childs() )  {
                log.info("Initializing server policy: " + policyConfigNode.get("policyClass").value() );
                
                Class<? extends IPolicy> policyClazz = (Class<? extends IPolicy>) Class.forName(policyConfigNode.get("policyClass").value() );
                Class<? extends IProtocol> policyProtocolClazz = (Class<? extends IProtocol>) Class.forName( policyConfigNode.get("protocolClass").value() );

                if ( !protocolImpl.getProtocolVersion().equals( policyConfigNode.get("protocolVersion").value() ) ) {
                    log.info("Version of policy protocol version is not compitable with this server instance...");
                    log.info("Actual protocol version: " + protocolImpl.getProtocolVersion() + "; Policy supports: " + policyConfigNode.get("protocolVersion").value() );
                    continue;
                }

                if ( !policyProtocolClazz.isAssignableFrom( protocolImpl.getClass() ) ) {
                    log.info("Policy cannot work with current server protocol...");
                    continue;
                }

                log.info("Adding policy " + policyClazz.getCanonicalName() + " to " + server.getClass().getCanonicalName() );
                server.addPolicy(
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
