package com.vio.server;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.api.dispatchers.vanilla.VanillaDispatcher;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.VersionRegistryFactory;
import com.vio.io.protocols.vanilla.VanillaVersionsRegistry;
import com.vio.server.listeners.IRequestsProcessor;
import com.vio.server.listeners.connection.ConnectionsListener;
import com.vio.server.listeners.connection.IConnectionListener;
import com.vio.server.listeners.request.ApiRequestListener;
import com.vio.server.listeners.request.IRequestListener;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SocketServerFactory extends AbstractServerFactory implements ISocketServerFactory {
    private static final Logger log = Logger.getLogger( SocketServerFactory.class );

    /**
     * Default requests dispatcher for each new server instance
     */
    public static final IDispatcher DEFAULT_DISPATCHER = new VanillaDispatcher();

    public final static IConnectionListener DEFAULT_CONNECTION_LISTENER = new ConnectionsListener();

    public final static IRequestsProcessor DEFAULT_REQUESTS_PROCESSOR = new ApiRequestListener();

    public final static Class<? extends IRequestListener> DEFAULT_REQUEST_LISTENER = ApiRequestListener.class;

    public static IProtocol DEFAULT_PROTOCOL;
    static {
        try {
           DEFAULT_PROTOCOL  = VersionRegistryFactory.getInstance(VanillaVersionsRegistry.class).getActualProtocol();
        } catch ( InstantiationException e ) {
            log.error( e.getMessage(), e );
        }
    }

    private IDispatcher dispatcher;

    private IProtocol protocol;

    private Class<? extends IRequestListener> requestsListener;

    private IRequestsProcessor requestsProcessor;

    private IConnectionListener connectionsListener;

    public SocketServerFactory() {
        this( DEFAULT_REQUEST_LISTENER, DEFAULT_REQUESTS_PROCESSOR, DEFAULT_CONNECTION_LISTENER, DEFAULT_DISPATCHER, new HashMap() );
    }

    public SocketServerFactory( Class<? extends IRequestListener> requestsListener,
                                IRequestsProcessor requestsProcessor, IConnectionListener connectionsListener,
                                IDispatcher dispatcher, Map<String, Object> properties ) {
        super(properties);

        this.requestsListener = requestsListener;
        this.requestsProcessor = requestsProcessor;
        this.connectionsListener = connectionsListener;
        this.dispatcher = dispatcher;
    }

    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, false );
    }

    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port, Boolean isSSLEnabled )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, this.getProperties() );
    }

    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port, Boolean isSSLEnabled, Map<String, Object> properties )
        throws InstantiationException {
        return (T) this.newInstance( (Class<? extends ISocketServer>) serverClass, host, port, isSSLEnabled, properties, this.getProtocol() );
    }

    public <T extends ISocketServer> T newInstance( Class<T> serverClass, String host,
                                Integer port, Boolean isSSLEnabled, Map<String, Object> properties, IProtocol protocol )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, properties, protocol, this.getDispatcher() );
    }

    public <T extends ISocketServer> T newInstance( Class<T> serverClass, String host, Integer port,
                                Boolean isSSLEnabled, Map<String, Object> properties, IProtocol protocol, IDispatcher dispatcher )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, properties, protocol, dispatcher, this.getRequestsListener() );
    }

    public <T extends ISocketServer> T newInstance( Class<T> serverClass, String host, Integer port,
                                Boolean isSSLEnabled, Map<String, Object> properties, IProtocol protocol,
                                IDispatcher dispatcher, Class<? extends IRequestListener> listener )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, properties, protocol, dispatcher, listener, this.getRequestsProcessor() );
    }

    public <T extends ISocketServer> T newInstance( Class<T> serverClass, String host, Integer port,
                                Boolean isSSLEnabled, Map<String, Object> properties, IProtocol protocol,
                                IDispatcher dispatcher, Class<? extends IRequestListener> listener,
                                IRequestsProcessor processor )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, properties, protocol, dispatcher, listener, processor, this.getConnectionsListener() );
    }

    public <T extends ISocketServer> T newInstance( Class<T> serverClass, String host, Integer port,
                                Boolean isSSLEnabled, Map<String, Object> properties, IProtocol protocol,
                                IDispatcher dispatcher, Class<? extends IRequestListener> listener,
                                IRequestsProcessor processor, IConnectionListener connectionsListener  )
        throws InstantiationException {
        try {
            T server = serverClass.newInstance();
            server.setHost(host);
            server.setPort(port);
            server.enableSSL(isSSLEnabled);
            server.setDispatcher( dispatcher );
            server.setProtocol( protocol );
            server.setRequestListener(listener);
            server.setRequestsProcessor(processor);
            server.setConnectionListener( connectionsListener );

            this.bindPolicies( server, this.getPolicies() );
            this.bindProperties( server, properties );

            return server;
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    /**
     * Set default requests listener for each product of current factory
     * @param listenerClass
     */
    public void setRequestsListener( Class<? extends IRequestListener> listenerClass ) {
        this.requestsListener = listenerClass;
    }

    public Class<? extends IRequestListener> getRequestsListener() {
        return this.requestsListener;
    }

    public void setRequestsProcessor( IRequestsProcessor processor ) {
        this.requestsProcessor = processor;
    }

    public IRequestsProcessor getRequestsProcessor() {
        return this.requestsProcessor;
    }

    public IDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public void setDispatcher( IDispatcher dispatcher ) {
        this.dispatcher = dispatcher;
    }

    public IProtocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol( IProtocol protocol ) {
        this.protocol = protocol;
    }

    public void setConnectionsListener( IConnectionListener connectionsListener ) {
        this.connectionsListener = connectionsListener;
    }

    public IConnectionListener getConnectionsListener() {
        return this.connectionsListener;
    }

    public static ISocketServerFactory getDefault() {
        return DefaultInstanceHandler.defaultInstance;
    }

    public static void setDefault( ISocketServerFactory factory ) {
        DefaultInstanceHandler.defaultInstance = factory;
    }

    final static class DefaultInstanceHandler {
        volatile private static ISocketServerFactory defaultInstance = createDefault();

        public static ISocketServerFactory createDefault() {
            return new SocketServerFactory();
        }
    }

}