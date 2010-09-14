package com.redshape.server.processors.request;

import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.server.ISocketServer;
import com.redshape.server.ServerException;
import com.redshape.server.adapters.socket.client.ISocketAdapter;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 24, 2010
 * Time: 12:49:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRequestsProcessor<
                                T extends ISocketServer<? extends IProtocol<G, ?, Q, ?>, Q>,
                                Q extends IResponse,
                                G extends IRequest,
                                V extends ISocketAdapter>
                implements IRequestsProcessor<T, G> {
    private static final Logger log = Logger.getLogger( AbstractRequestsProcessor.class );
    

    private V socket;
    private T server;

    @Override
    public void setServerContext( T server ) {
        this.server = server;
    }

    @Override
    public T getServerContext() {
        return this.server;
    }

    protected boolean authenticateRequest( G request ) throws ServerException {
        try {
//            boolean result = false;
//            if ( this.getServerContext().checkPolicy( this.getServerContext().getProtocol().getClass(), PolicyType.ON_REQUEST, request ) ) {
//                result = true;
//            }
//
//            return result;

            return true;
        } catch ( Throwable e ) {
            throw new ServerException();
        }
    }

}
