package com.redshape.server.processors.connection;

import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.ProtocolException;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.server.ISocketServer;
import com.redshape.server.ServerException;
import com.redshape.server.adapters.socket.client.ISocketAdapter;
import com.redshape.server.processors.request.IRequestsProcessor;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Dec 23, 2009
 * Time: 11:34:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientsProcessor implements IClientsProcessor {
    private static final Logger log = Logger.getLogger( ClientsProcessor.class );
    public static int MAX_WORKER_WARNS = 12;
    
    private ISocketServer context;
    
    private int errorsTick;
    
    private boolean isValid = true;

    public void setContext( ISocketServer server ) {
    	this.context = server;
    }
    
    protected ISocketServer getContext() {
    	return this.context;
    }
    
    public boolean isValid() {
        return this.isValid && this.errorsTick < MAX_WORKER_WARNS;
    }

    public void markInvalid() {
        this.isValid = false;
    }

    public void incrErrorsTick() {
        this.errorsTick += 1;
    }

    /**
     * Поставить текущего пользователя в очередь
     * @param socket
     * @throws java.io.IOException
     * @throws BalanserOverloadedException
     */
     @Override
     public boolean onConnection( final ISocketAdapter socket ) throws ServerException {
        log.info("Processing adapters: " + socket.getRemoteSocketAddress() );

        try {
            IProtocol protocol = this.getContext().getProtocol();
            IRequestsProcessor processor = protocol.createRequestsProcessor( this.getContext() );
            processor.setServerContext(this.getContext());
            
            BufferedInput input = new BufferedInput( socket.getInputStream() );
            while ( this.isValid() ) {
                log.info("Waiting request...");
                try{
                    IRequest request = protocol.readRequest( input );
                    if ( request == null ) {
                        this.incrErrorsTick();
                        continue;
                    }

                    request.setSocket( socket );

                    log.info("Processing request...");
                    processor.onRequest(request);
                } catch ( ExceptionWithCode e ) {
                    this.getContext().writeResponse( socket, e );
                    this.incrErrorsTick();
                } catch ( Throwable e ) {
                    this.incrErrorsTick();
                } 
            }
        } catch ( ProtocolException e) {
            log.error("Protocol related exception", e );
        } catch ( Throwable e ) {
            log.error("Internal exception", e );
        }
        
        return true;
    }

}
