package com.vio.server.listeners.request;

import com.vio.api.dispatchers.http.IHttpDispatcher;
import com.vio.io.protocols.http.IHttpProtocol;
import com.vio.io.protocols.http.request.IHttpRequest;
import com.vio.io.protocols.http.response.IHttpResponse;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 8:21:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestListener extends AbstractRequestListener<ISocketServer<IHttpProtocol<IHttpRequest, IHttpResponse>, IHttpDispatcher, IHttpResponse>, IHttpResponse, IHttpRequest, ISocketAdapter> {
    private static final Logger log = Logger.getLogger( HttpRequestListener.class );

    public void onRequest( IHttpRequest request ) throws ServerException {
        try {
            if ( !this.authenticateRequest(request) ) {
                log.info("Failed authentication");
                this.markInvalid();
                return;
            }

            IHttpResponse response = this.getContext().createResponseObject();

            ISocketAdapter socket = request.getSocket();
            log.info("Processing request" );

            log.info("Processing start time: " + new Date( new Date().getTime() ).toLocaleString() );
            long startTime = System.currentTimeMillis();

            try {
                this.getContext().getDispatcher().dispatch( null, request, response );

                log.info("Sending response to client...");
                this.getContext().writeResponse( socket, response );

                log.info("Processing time: " + ( System.currentTimeMillis() - startTime ) );
            } catch ( ServerException e ) {
                log.error( e.getMessage(), e );
                this.getContext().writeResponse( socket, e );
            }


        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }

    }

}
