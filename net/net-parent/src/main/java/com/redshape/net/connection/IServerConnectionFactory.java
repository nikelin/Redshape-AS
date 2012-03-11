package com.redshape.net.connection;

import com.redshape.net.IServer;
import com.redshape.net.ServerType;

/**
 * @author nikelin
 * @date 12:57
 */
public interface IServerConnectionFactory {

    public void registerSupport( ServerType type, Class<? extends IServerConnection> connectionSupport );
    
    public IServerConnection createConnection( IServer server );
    
}
