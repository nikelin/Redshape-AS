package com.redshape.io.net.adapters.socket;

import java.util.HashMap;
import java.util.Map;

import com.redshape.io.net.adapters.socket.client.ISocketAdapterFactory;
import com.redshape.io.net.adapters.socket.server.IServerSocketAdapterFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 7, 2010
 * Time: 11:56:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SocketAdapterFactory implements ISocketAdapterFactory, IServerSocketAdapterFactory {
    private static SocketAdapterFactory defaultInstance = new DefaultSocketAdapterFactory();

    private static Map<Class<? extends SocketAdapterFactory>, SocketAdapterFactory> factories = new HashMap<
                                                                                            Class<? extends SocketAdapterFactory>,
                                                                                            SocketAdapterFactory
                                                                                         >();

    public static SocketAdapterFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( SocketAdapterFactory factory ) {
        defaultInstance = factory;
    }

    public static SocketAdapterFactory getFactory( Class<? extends SocketAdapterFactory> clazz )
                                                                            throws InstantiationException
    {
        SocketAdapterFactory factory = factories.get(clazz);
        if ( factory != null ) {
            return factory;
        }

        try {
            factory = clazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }

        factories.put( clazz, factory );

        return factory;
    }
}
