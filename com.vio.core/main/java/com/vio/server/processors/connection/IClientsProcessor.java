package com.vio.server.processors.connection;

import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:03:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IClientsProcessor {

    public void setContext( ISocketServer server );

    public boolean onConnection( ISocketAdapter connection ) throws ServerException;    
}
