package com.vio.server.listeners.request;

import com.vio.api.dispatchers.vanilla.IVanillaDispatcher;
import com.vio.io.protocols.vanilla.IVanillaProtocol;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import com.vio.io.protocols.vanilla.request.InterfaceInvocation;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.listeners.IRequestsProcessor;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:38:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiRequestListener extends AbstractRequestListener<ISocketServer<IVanillaProtocol<IAPIRequest, IApiResponse>, IVanillaDispatcher, IApiResponse>, IApiResponse, IAPIRequest, ISocketAdapter> implements IRequestsProcessor<IAPIRequest> {
    private static final Logger log = Logger.getLogger( ApiRequestListener.class);

    @Override
    public void onRequest( IAPIRequest request ) throws ServerException {
        if ( !this.authenticateRequest(request) ) {
            log.info("Failed authentication");
            this.markInvalid();
            return;
        }

        IApiResponse response = this.getContext().createResponseObject();

        ISocketAdapter socket = request.getSocket();
        log.info("Processing request" );
        log.info( "Invokes count: " + request.getInvokes().size() );
        for ( InterfaceInvocation invoke : request.getInvokes() ) {
            log.info("Invoke id: " + invoke.getId() );
            log.info("Processing start time: " + new Date( new Date().getTime() ).toLocaleString() );
            long startTime = System.currentTimeMillis();
            IApiResponse currentResponse = this.getContext().createResponseObject();
            currentResponse.setId( invoke.getId() );

            try {
                this.getContext().getDispatcher().dispatch(
                							request.getIdentity(),
                							invoke,
                							currentResponse );

                if ( request.isAsync() ) {
                    log.info("Sending response to client in async mode...");
                    this.getContext().writeResponse( socket, currentResponse );
                } else {
                    response.addResponse( currentResponse );
                }

                log.info("Processing time: " + ( System.currentTimeMillis() - startTime ) );
            } catch ( ServerException e ) {
                log.error( e.getMessage(), e );
                this.getContext().writeResponse( socket, e );
            }
        }

        if ( !request.isAsync() ) {
            log.info("Sending response in synchoronized mode...");
            log.info( socket.getClass() );
            this.getContext().writeResponse( socket, response.getResponses() );
        }
    }

}
