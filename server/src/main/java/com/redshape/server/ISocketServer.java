package com.redshape.server;

import com.redshape.api.requesters.IRequester;
import com.redshape.config.ConfigException;
import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.net.adapters.socket.server.IServerSocketAdapter;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.server.IServer;
import com.redshape.io.server.ServerException;
import com.redshape.exceptions.ExceptionWithCode;

import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.server
 * @date Apr 14, 2010
 */
public interface ISocketServer<T extends IProtocol, R extends IResponse>
                 extends IServer {

    public boolean isConnectionExpired( IRequester user ) throws ConfigException;

    public IServerSocketAdapter getSocket();
    
    public ISocketAdapter getLocalSocket();

    public void setLocalSocket( ISocketAdapter socket );

    public void closeLocalSocket();

    public void refuseConnection( ISocketAdapter socket ) throws ServerException;

    @Deprecated
    public R createResponseObject() throws ServerException;

    public void writeResponse( ISocketAdapter socket, R response ) throws ServerException;

    public void writeResponse( ISocketAdapter socket, Throwable exception ) throws ServerException;

    public void writeResponse( ISocketAdapter socket, Collection<R> responses ) throws ServerException;

    public void setProtocol( T protocol );

    public T getProtocol();
}
