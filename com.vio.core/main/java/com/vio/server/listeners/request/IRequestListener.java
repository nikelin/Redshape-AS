package com.vio.server.listeners.request;

import com.vio.io.protocols.core.request.IRequest;
import com.vio.server.IServer;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;

import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:03:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IRequestListener<T extends ISocketServer, G extends IRequest, V extends ISocketAdapter> {

    public void setContext( T context );

    public T getContext();

    public void onConnection( V socket ) throws ServerException;

    public void onRequest( G request ) throws ServerException;

}
