package com.vio.server;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.api.dispatchers.vanilla.VanillaDispatcher;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.VersionRegistryFactory;
import com.vio.io.protocols.vanilla.VanillaVersionsRegistry;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SocketServerFactory extends AbstractServerFactory implements ISocketServerFactory {
    private static final Logger log = Logger.getLogger( SocketServerFactory.class );

    /**
     * Default requests dispatcher for each new server instance
     */
    public static final IDispatcher DEFAULT_DISPATCHER = new VanillaDispatcher();

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

    public SocketServerFactory() {
        this( DEFAULT_DISPATCHER, new HashMap() );
    }

    public SocketServerFactory( IDispatcher dispatcher, Map<String, Object> properties ) {
        super(properties);

        this.dispatcher = dispatcher;
    }

    @Override
    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, false );
    }

    @Override
    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port, Boolean isSSLEnabled )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, this.getProperties() );
    }

    @Override
    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port, Boolean isSSLEnabled, Map<String, Object> properties )
        throws InstantiationException {
        return (T) this.newInstance( (Class<? extends ISocketServer>) serverClass, host, port, isSSLEnabled, properties, this.getProtocol() );
    }

    @Override
    public <T extends ISocketServer> T newInstance( Class<T> serverClass, String host,
                                Integer port, Boolean isSSLEnabled, Map<String, Object> properties, IProtocol protocol )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, properties, protocol, this.getDispatcher() );
    }

    @Override
    public <T extends ISocketServer> T newInstance( Class<T> serverClass, String host, Integer port, Boolean isSSLEnabled,
                                                    Map<String, Object> properties, IProtocol protocol,
                                                    IDispatcher dispatcher  )
        throws InstantiationException {
        try {
            T server = serverClass.newInstance();
            server.setHost(host);
            server.setPort(port);
            server.enableSSL(isSSLEnabled);
            server.setDispatcher( dispatcher );
            server.setProtocol( protocol );

            this.bindPolicies( server, this.getPolicies() );
            this.bindProperties( server, properties );

            return server;
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    @Override
    public IDispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Override
    public void setDispatcher( IDispatcher dispatcher ) {
        this.dispatcher = dispatcher;
    }

    @Override
    public IProtocol getProtocol() {
        return this.protocol;
    }

    @Override
    public void setProtocol( IProtocol protocol ) {
        this.protocol = protocol;
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