package com.vio.server.listeners.api;

import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.vanilla.request.APIRequest;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import com.vio.io.protocols.vanilla.request.InterfaceInvocation;
import com.vio.io.protocols.vanilla.response.ApiResponse;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.io.protocols.sources.input.BufferedInput;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.listeners.AbstractRequestListener;
import com.vio.server.listeners.IRequestsProcessor;
import com.vio.server.policy.PolicyType;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:38:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiRequestListener extends AbstractRequestListener<ISocketServer, ISocketAdapter> implements IRequestsProcessor<IAPIRequest> {
    private static final Logger log = Logger.getLogger( ApiRequestListener.class);
    public static int MAX_TICKS = 12;

    private int errorsTick;
    private boolean is_valid = true;

    private boolean is_authenticated;
    private IRequester authenticatedIdentity;

    public boolean isValid() {
        return this.is_valid && this.errorsTick < MAX_TICKS;
    }

    public void markInvalid() {
        this.is_valid = false;
    }

    @Override
    public void onConnection( ISocketAdapter socket ) throws ServerException {
        try {
            BufferedInput input = new BufferedInput( socket.getInputStream() );

            while ( this.isValid() ) {
                log.info("Waiting request...");
                try{
                    APIRequest request = this.getContext().getReader().readRequest( input );
                    if ( request == null ) {
                        this.incrErrorsTick();
                        Thread.sleep(500);
                        continue;
                    }
                    
                    request.setSocket( socket );

                    if ( this.authenticateRequest(request) ) {
                        log.info("Prcessing request...");
                        this.onRequest(request);
                    } else {
                        log.info("Failed authentication");
                        this.markInvalid();
                    }
                } catch ( ExceptionWithCode e ) {
                    log.error( e.getMessage(), e );
                    this.getContext().writeResponse( socket, e );
                    this.incrErrorsTick();
                } catch ( Throwable e ) {
                    log.error( e.getMessage(), e );
                    this.incrErrorsTick();
                }
            }
       } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServerException();
       }
    }

    private boolean authenticateRequest( IAPIRequest request ) throws ExceptionWithCode {
        boolean result = false;
        if ( this.is_authenticated ) {
            request.setIdentity( this.authenticatedIdentity );
            result = true;
        } else {
            if ( this.getContext().checkPolicy( PolicyType.ON_REQUEST, request ) ) {
                this.is_authenticated = true;
                this.authenticatedIdentity = request.getIdentity();
                result = true;
            }
        }

        return result;
    }

    public void incrErrorsTick() {
        this.errorsTick += 1;
    }

    @Override
    public void onRequest( IAPIRequest request ) throws ServerException {
        IApiResponse response = this.getContext().createResponseObject();

        ISocketAdapter socket = request.getSocket();
        log.info("Processing request" );
        log.info( "Invokes count: " + request.getBody().size() );
        for ( InterfaceInvocation invoke : request.getBody() ) {
            log.info("Invoke id: " + invoke.getId() );
            log.info("Processing start time: " + new Date( new Date().getTime() ).toLocaleString() );
            long startTime = System.currentTimeMillis();
            IApiResponse currentResponse = new ApiResponse( invoke.getId() );

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
