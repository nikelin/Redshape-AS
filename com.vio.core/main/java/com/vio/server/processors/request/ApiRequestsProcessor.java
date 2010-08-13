package com.vio.server.processors.request;

import com.vio.api.dispatchers.vanilla.IVanillaDispatcher;
import com.vio.io.protocols.core.request.RequestType;
import com.vio.io.protocols.vanilla.IVanillaProtocol;
import com.vio.io.protocols.vanilla.request.IApiRequest;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
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
                        IVanillaProtocol<IApiRequest, IVanillaDispatcher, IApiResponse, ?>,
                        IApiResponse
                    >, IApiResponse, IApiRequest, ISocketAdapter> {
    
    private static final Logger log = Logger.getLogger( ApiRequestsProcessor.class);

    @Override
    public void onRequest( IApiRequest request ) throws ServerException {
        if ( !this.authenticateRequest(request) ) {
            log.info("Failed authentication");
            throw new ServerException();
        }

        IApiResponse response = this.getServerContext().createResponseObject();

        IVanillaProtocol protocol = this.getServerContext().getProtocol();
        ISocketAdapter socket = request.getSocket();
        log.info("Processing request" );
        log.info( "Invokes count: " + request.getChildren().size() );
        for ( IApiRequest invoke : request.getChildren() ) {
            log.info("Invoke id: " + invoke.getId() );
            log.info("Processing start time: " + new Date( new Date().getTime() ).toLocaleString() );
            long startTime = System.currentTimeMillis();
            IApiResponse currentResponse = this.getServerContext().createResponseObject();
            currentResponse.setId( invoke.getId() );

            try {
                IVanillaDispatcher dispatcher = (IVanillaDispatcher) protocol.getRequestDispatcher( request.getType() != null ? request.getType() : RequestType.INTERFACE_INVOKE);
                log.info( "Request type: " + request.getType() );
                log.info( "Request dispatcher: " + dispatcher.getClass().getCanonicalName() );
                dispatcher.dispatch(
                    request.getIdentity(),
                    invoke,
                    currentResponse
                );

                if ( request.isAsync() ) {
                    log.info("Sending response to client in async mode...");
                    this.getServerContext().writeResponse( socket, currentResponse );
                } else {
                    response.addResponse( currentResponse );
                }

                log.info("Processing time: " + ( System.currentTimeMillis() - startTime ) );
            } catch ( ServerException e ) {
                log.error( e.getMessage(), e );
                this.getServerContext().writeResponse( socket, e );
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                break;
            }
        }

        if ( !request.isAsync() ) {
            log.info("Sending response in synchoronized mode...");
            log.info( socket.getClass() );
            this.getServerContext().writeResponse( socket, response.getResponses() );
        }
    }

}
