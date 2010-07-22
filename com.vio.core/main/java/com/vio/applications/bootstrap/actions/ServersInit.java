package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.Action;
import com.vio.applications.bootstrap.BootstrapException;
import com.vio.config.IServerConfig;
import com.vio.server.IServer;
import com.vio.server.ISocketServer;
import com.vio.server.ServerFactory;
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

        Class<? extends ISocketServer> clazz;
        try {
            clazz = (Class<? extends ISocketServer>) Class.forName( serverClass );
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
            IServer server = ServerFactory.getInstance().createInstance( clazz, host, port, isSSLEnabled );

            for ( String policyClass : config.getServerPolicies( serverName ) )  {
                log.info(config.getServerPolicyType( serverName, policyClass ) );
                server.addPolicy(
                    PolicyType.valueOf( config.getServerPolicyType( serverName, policyClass ) ),
                    PoliciesFactory.getDefault().createPolicy( (Class<? extends IPolicy>) Class.forName(policyClass), server )
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

}
