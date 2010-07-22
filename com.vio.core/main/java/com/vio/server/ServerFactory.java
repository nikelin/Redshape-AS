package com.vio.server;

import com.vio.api.dispatchers.Dispatcher;
import com.vio.api.dispatchers.impl.DispatcherImpl;
import com.vio.exceptions.ErrorCode;
import com.vio.server.adapters.socket.server.IServerSocketAdapter;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;

/**
 * Фабрика серверов
 *
 * @author nikelin
 */
public class ServerFactory {
    private final static Logger log = Logger.getLogger( ServerFactory.class );
	private final static ServerFactory instance = new ServerFactory(); 
    private IServerSocketAdapter defaultAdapter;

    private Map<Class<? extends IServer>, IServer> servers =
                                                                new HashMap<Class<? extends IServer>, IServer>();
    private Dispatcher dispatcher = new DispatcherImpl();

    public static ServerFactory getInstance() {
        return instance;
    }

    private ServerFactory() {}

    public void setDispatcher( Dispatcher dispatcher ) {
        this.dispatcher = dispatcher;
    }

    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    public <T extends IServer> T getInstance( Class<? extends T> clazz ) {
        return (T) this.servers.get( clazz );
    }

    public <T extends IServer> T createInstance( Class<? extends T> clazz, String host, Integer port, Boolean isSSLEnabled ) throws ServerException {
        if ( this.isRegistered( clazz, host, port ) ) {
            throw new ServerException( ErrorCode.EXCEPTION_SERVER_ALREADY_BINDED);
        }

        try {
            log.info("Binding server " + clazz.getCanonicalName() + " to " + host + ":" + port );
            T server = clazz.newInstance();
            server.setHost( host );
            server.setPort( port );
            server.enableSSL( isSSLEnabled );

            this.servers.put(clazz, server);

            if ( ISocketServer.class.isAssignableFrom( server.getClass() ) ) {
                this.configureApiInstance( (ISocketServer) server );
            }

            return server;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServerException();
        }
    }

    private void configureApiInstance( ISocketServer serverInstance ) {
        serverInstance.setDispatcher( this.getDispatcher() );
    }

    public boolean isRegistered( Class<? extends IServer> clazz, String host, Integer port ) {
        return this.servers.containsKey(clazz)
                && this.servers.get(clazz).getHost().equals( host )
                    && this.servers.get(clazz).getPort().equals(port);
    }

}
