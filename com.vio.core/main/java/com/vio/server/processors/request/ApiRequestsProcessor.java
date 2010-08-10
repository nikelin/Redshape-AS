package com.vio.server.processors.request;

import com.vio.api.dispatchers.vanilla.IVanillaDispatcher;
import com.vio.io.protocols.vanilla.IVanillaProtocol;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
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
                        IVanillaProtocol<IAPIRequest, IApiResponse, ?>,
                        IVanillaDispatcher,
                        IApiResponse
                    >, IApiResponse, IAPIRequest, ISocketAdapter> {
    
    private static final Logger log = Logger.getLogger( ApiRequestsProcessor.class);

    @Override
    public void onRequest( IAPIRequest request ) throws ServerException {
        if ( !this.authenticateRequest(request) ) {
            log.info("Failed authentication");
            throw new ServerException();
        }

        IApiResponse response = this.getServerContext().createResponseObject();

        ISocketAdapter socket = request.getSocket();
        log.info("Processing request" );
        log.info( "Invokes count: " + request.getChildren().size() );
        for ( IAPIRequest invoke : request.getChildren() ) {
            log.info("Invoke id: " + invoke.getId() );
            log.info("Processing start time: " + new Date( new Date().getTime() ).toLocaleString() );
            long startTime = System.currentTimeMillis();
            IApiResponse currentResponse = this.getServerContext().createResponseObject();
            currentResponse.setId( invoke.getId() );

            try {
                this.getServerContext().getDispatcher().dispatch(
                                                        request.getIdentity(),
                                                        invoke,
                                                        currentResponse );

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
            }
        }

        if ( !request.isAsync() ) {
            log.info("Sending response in synchoronized mode...");
            log.info( socket.getClass() );
            this.getServerContext().writeResponse( socket, response.getResponses() );
        }
    }

}
