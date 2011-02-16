package com.redshape.server;

import com.redshape.api.dispatchers.vanilla.InterfaceInvocationsDispatcher;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.VersionRegistryFactory;
import com.redshape.io.protocols.dispatchers.IDispatcher;
import com.redshape.io.protocols.vanilla.VanillaVersionsRegistry;
import com.redshape.io.server.IServer;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SocketServerFactory extends AbstractServerFactory implements ISocketServerFactory {
    private static final Logger log = Logger.getLogger( SocketServerFactory.class );

    public static IProtocol DEFAULT_PROTOCOL;
    static {
        try {
           DEFAULT_PROTOCOL  = VersionRegistryFactory.getInstance(VanillaVersionsRegistry.class).getActualProtocol();
        } catch ( InstantiationException e ) {
            log.error( e.getMessage(), e );
        }
    }

    private IProtocol protocol;

    public SocketServerFactory() {
        this( new HashMap() );
    }

    public SocketServerFactory( Map<String, Object> properties ) {
        super(properties);
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
        try {
            T server = serverClass.newInstance();
            server.setHost(host);
            server.setPort(port);
            server.enableSSL(isSSLEnabled);
            server.setProtocol( protocol );

            this.bindPolicies( server, this.getPolicies() );
            this.bindProperties( server, properties );

            return server;
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
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