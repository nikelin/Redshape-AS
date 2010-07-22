package com.vio.server;

import com.vio.api.dispatchers.Dispatcher;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.io.protocols.hydrators.RequestHydrator;
import com.vio.io.protocols.renderers.ResponseRenderer;
import com.vio.config.readers.ConfigReaderException;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.listeners.IConnectionListener;
import com.vio.server.listeners.IRequestListener;
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
public interface ISocketServer<T extends RequestHydrator,
                            V extends ResponseRenderer,
                            R extends IApiResponse>
                 extends IServer<R> {

    public void setDispatcher( Dispatcher dispatcher );

    public void setRequestHydrator( T hydrator );

    public T getRequestHydrator();

    public void setResponseRenderer( V renderer );

    public V getResponseRenderer();

    public boolean isConnectionExpired( IRequester user ) throws ConfigReaderException;

    public void setConnectionListener( IConnectionListener listener );

    public IConnectionListener getConnectionListener();

    public void setRequestListener( Class<? extends IRequestListener> listener );

    public void setRequestsProcessor( IRequestsProcessor processor );

    public IRequestsProcessor getRequestsProcessor();

    public Dispatcher getDispatcher();

    public ISocketAdapter getLocalSocket();

    public void setLocalSocket( ISocketAdapter socket );

    public void closeLocalSocket();

    public IRequestListener createRequestListener( ) throws ServerException;

    public void refuseConnection( ISocketAdapter socket ) throws ServerException;

    public R createResponseObject() throws ServerException;

    public void writeResponse( ISocketAdapter socket, R response ) throws ServerException;

    public void writeResponse( ISocketAdapter socket, ExceptionWithCode exception ) throws ServerException;

    public void writeResponse( ISocketAdapter socket, Collection<R> responses ) throws ServerException;

}
