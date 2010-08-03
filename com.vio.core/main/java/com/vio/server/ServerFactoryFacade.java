package com.vio.server;

import com.vio.server.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 2:31:04 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ServerFactoryFacade {
    public static Class<? extends IServerFactory> DEFAULT_SERVER_FACTORY = ServerFactory.class;

    public static Class<? extends ISocketServerFactory> DEFAULT_SOCKET_SERVER_FACTORY = SocketServerFactory.class;

    /**
     * @TODO Refactor static logic
     * @param clazz
     * @return
     * @throws InstantiationException
     */
    public static IServerFactory createFactory( Class<? extends IServer> clazz )
        throws InstantiationException {
        if ( ISocketServer.class.isAssignableFrom(clazz) ) {
            return createSocketServerFactory();
        } else if ( IServer.class.isAssignableFrom(clazz) ) {
            return createServerFactory();
        }

        throw new InstantiationException();
    }

    public static IServerFactory createServerFactory() throws InstantiationException {
        try {
            return DEFAULT_SERVER_FACTORY.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    public static ISocketServerFactory createSocketServerFactory() throws InstantiationException {
        try {
            return DEFAULT_SOCKET_SERVER_FACTORY.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

}
