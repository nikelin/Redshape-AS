package com.vio.server.listeners.connection;

import com.vio.server.IServer;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;

import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:03:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IConnectionListener {

    public void setServer( ISocketServer server );

    public boolean onConnection( ISocketAdapter connection ) throws ServerException;    
}
