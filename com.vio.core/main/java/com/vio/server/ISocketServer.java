package com.vio.server;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.response.IResponse;
import com.vio.config.readers.ConfigReaderException;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.listeners.connection.IConnectionListener;
import com.vio.server.listeners.request.IRequestListener;
import com.vio.server.listeners.IRequestsProcessor;

import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.server
 * @date Apr 14, 2010
 */
public interface ISocketServer<T extends IProtocol, D extends IDispatcher, R extends IResponse>
                 extends IServer {

    public void setDispatcher( D dispatcher );

    public boolean isConnectionExpired( IRequester user ) throws ConfigReaderException;

    public void setConnectionListener( IConnectionListener listener );

    public IConnectionListener getConnectionListener();

    public void setRequestListener( Class<? extends IRequestListener> listener );

    public void setRequestsProcessor( IRequestsProcessor processor );

    public IRequestsProcessor getRequestsProcessor();

    public D getDispatcher();

    public ISocketAdapter getLocalSocket();

    public void setLocalSocket( ISocketAdapter socket );

    public void closeLocalSocket();

    public IRequestListener createRequestListener( ) throws ServerException;

    public void refuseConnection( ISocketAdapter socket ) throws ServerException;

    public R createResponseObject() throws ServerException;

    public void writeResponse( ISocketAdapter socket, R response ) throws ServerException;

    public void writeResponse( ISocketAdapter socket, ExceptionWithCode exception ) throws ServerException;

    public void writeResponse( ISocketAdapter socket, Collection<R> responses ) throws ServerException;

    public void setProtocol( T protocol );

    public T getProtocol();
}
