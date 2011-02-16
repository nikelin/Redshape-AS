package com.redshape.server.processors.connection;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.server.ServerException;
import com.redshape.server.ISocketServer;

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
