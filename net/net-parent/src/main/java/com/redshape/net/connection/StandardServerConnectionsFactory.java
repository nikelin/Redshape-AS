package com.redshape.net.connection;

import com.redshape.net.IServer;
import com.redshape.net.ServerType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/19/12
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardServerConnectionsFactory implements IServerConnectionFactory {
    private Map<ServerType, Class<? extends IServerConnection>> registry
            = new HashMap<ServerType, Class<? extends IServerConnection>>();

    public StandardServerConnectionsFactory(Map<ServerType, Class<? extends IServerConnection>> registry) {
        this.registry = registry;
    }

    @Override
    public void registerSupport(ServerType type, Class<? extends IServerConnection> connectionSupport) {
        this.registry.put( type, connectionSupport );
    }

    @Override
    public IServerConnection createConnection(IServer server) {
        Class<? extends IServerConnection> connection = this.registry.get(server);
        if ( connection == null ) {
            throw new IllegalArgumentException("Server type not supported");
        }

        IServerConnection conn;
        try {
            conn = connection.newInstance();
        } catch ( Throwable e ) {
            throw new IllegalArgumentException( e.getMessage(), e );
        }

        return conn;
    }
}
