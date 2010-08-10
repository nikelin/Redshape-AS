package com.vio.server.processors.request;

import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.response.IResponse;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.policy.PolicyType;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 24, 2010
 * Time: 12:49:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRequestsProcessor<T extends ISocketServer<? extends IProtocol<G, Q, ?>, ?, Q>, Q extends IResponse, G extends IRequest, V extends ISocketAdapter> implements IRequestsProcessor<T, G> {
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
