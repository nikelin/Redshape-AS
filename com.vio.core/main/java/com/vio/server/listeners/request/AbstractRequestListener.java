package com.vio.server.listeners.request;

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
public abstract class AbstractRequestListener<T extends ISocketServer<? extends IProtocol<G, Q>, ?, Q>, Q extends IResponse, G extends IRequest, V extends ISocketAdapter> implements IRequestListener<T, G, V> {
    private static final Logger log = Logger.getLogger( AbstractRequestListener.class );
    public static int MAX_TICKS = 12;

    private V socket;
    private T context;
    
    public T getContext() {
        return this.context;
    }

    public void setContext( T context ) {
        this.context = context;
    }

    private int errorsTick;
    private boolean is_valid = true;


    public boolean isValid() {
        return this.is_valid && this.errorsTick < MAX_TICKS;
    }

    public void markInvalid() {
        this.is_valid = false;
    }

    public void incrErrorsTick() {
        this.errorsTick += 1;
    }

    protected boolean authenticateRequest( G request ) throws ServerException {
        try {
            boolean result = false;
            if ( this.getContext().checkPolicy( this.getContext().getProtocol().getClass(), PolicyType.ON_REQUEST, request ) ) {
                result = true;
            }

            return result;
        } catch ( ServerException e ) {
            throw e;
        } catch ( Throwable e ) {
            throw new ServerException();
        }
    }

    @Override
    public void onConnection( ISocketAdapter socket ) throws ServerException {
        try {
            BufferedInput input = new BufferedInput( socket.getInputStream() );

            while ( this.isValid() ) {
                log.info("Waiting request...");
                try{
                    G request = (G) this.getContext().getProtocol().readRequest( input );
                    if ( request == null ) {
                        this.incrErrorsTick();
                        Thread.sleep(500);
                        continue;
                    }

                    request.setSocket( socket );

                    log.info("Prcessing request...");
                    this.onRequest(request);
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

}
