package com.redshape.server.processors.request;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.dispatchers.IVanillaDispatcher;
import com.redshape.io.protocols.vanilla.IVanillaProtocol;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.io.server.ServerException;

import com.redshape.server.ISocketServer;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:38:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiRequestsProcessor
        extends AbstractRequestsProcessor<
                            ISocketServer<
                                IVanillaProtocol<IApiRequest, IVanillaDispatcher, IApiResponse>,
                                IApiResponse
                            >, IApiResponse, IApiRequest> {
    
    private static final Logger log = Logger.getLogger( ApiRequestsProcessor.class);

    @Override
    public void onRequest( IApiRequest request ) throws ServerException {
        if ( !this.authenticateRequest(request) ) {
            log.info("Failed authentication");
            throw new ServerException();
        }

        IApiResponse response = this.getServerContext().createResponseObject();

        ISocketAdapter socket = request.getSocket();
        log.info("Processing request" );
        log.info( "Invokes count: " + request.getChildren().size() );

        try {
            if ( request.hasChilds() ) {
                for ( IApiRequest invoke : request.getChildren() ) {
                    this.processInvoke( socket, this.getServerContext().getProtocol(), invoke, response );
                }
            } else {
                this.processInvoke( socket, this.getServerContext().getProtocol(), request, response );
            }
         } catch ( ServerException e ) {
            log.error( e.getMessage(), e );
            this.getServerContext().writeResponse( socket, e );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServerException();
        }

        if ( !request.isAsync() ) {
            log.info("Sending response in synchoronized mode...");
            log.info( socket.getClass() );
            this.getServerContext().writeResponse( socket, response.getResponses() );
        }
    }

    private void processInvoke( ISocketAdapter socket, IVanillaProtocol<?, IVanillaDispatcher, ?> protocol, IApiRequest invoke, IApiResponse response ) throws ServerException {
        log.info("Invoke id: " + invoke.getId() );
        log.info("Processing start time: " + new Date( new Date().getTime() ).toLocaleString() );
        long startTime = System.currentTimeMillis();
        IApiResponse currentResponse = this.getServerContext().createResponseObject();
        currentResponse.setId( invoke.getId() );

        IVanillaDispatcher dispatcher = protocol.getRequestDispatcher( invoke.getType() != null ? invoke.getType() : RequestType.INTERFACE_INVOKE);
        log.info( "Request type: " + invoke.getType() );
        log.info( "Request dispatcher: " + dispatcher.getClass().getCanonicalName() );
        log.info( "Current invoke identity: " + invoke.getIdentity() );
        log.info( "Parent identity invoke: " + invoke.getParent().getIdentity() );
        
        dispatcher.dispatch(
            invoke.getParent() == null ? invoke.getIdentity() : invoke.getParent().getIdentity(),
            invoke,
            currentResponse
        );

        if ( invoke.isAsync() ) {
            log.info("Sending response to client in async mode...");
            this.getServerContext().writeResponse( socket, currentResponse );
        } else {
            response.addResponse( currentResponse );
        }

        log.info("Processing time: " + ( System.currentTimeMillis() - startTime ) );
    }

}
