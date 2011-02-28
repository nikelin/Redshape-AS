package com.redshape.server.processors.request;

import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.server.ServerException;
import com.redshape.io.server.policy.ApplicationResult;
import com.redshape.io.server.policy.PolicyType;

import com.redshape.server.ISocketServer;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 24, 2010
 * Time: 12:49:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRequestsProcessor<
								P extends IProtocol<?, G, ?, ?, ?, Q>,
                                T extends ISocketServer<P, Q, G>,
                                Q extends IResponse,
                                G extends IRequest>
                implements IRequestsProcessor<T, G> {
    private static final Logger log = Logger.getLogger( AbstractRequestsProcessor.class );
    
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
            @SuppressWarnings("unchecked")
			ApplicationResult result = this.getServerContext().checkPolicy( (Class<P>) this.getServerContext().getProtocol().getClass(), PolicyType.ON_REQUEST, request );
            if ( !result.isSuccessful() ) {
                return false;
            }

            if ( result.isVoid() ) {
                log.error("There is no authentication policy applied to the current server instance. " +
                        "It is inappropriate situation in a production mode. Client request will be refused...");
                return false;
            }

            if ( result.isException() ) {
                log.error( "Unexpected authentication exception", result.getException() );
                throw new ServerException();
            }

            return true;
        } catch ( Throwable e ) {
            throw new ServerException();
        }
    }

}
