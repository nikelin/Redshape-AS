package com.redshape.server.processors.connection;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.ProtocolException;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.server.ServerException;
import com.redshape.server.ISocketServer;
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
    
    private ISocketServer<?,?,?> context;
    
    private int errorsTick;
    
    private boolean isValid = true;

    public void setContext( ISocketServer<?, ?, ?> server ) {
    	this.context = server;
    }
    
    protected ISocketServer<?,?,?> getContext() {
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
     * @param socket
     * @throws java.io.IOException
     */
     @Override
     public boolean onConnection( final ISocketAdapter socket ) throws ServerException {
        log.info("Processing adapters: " + socket.getRemoteSocketAddress() );

        try {
            IProtocol<?, ?,?,?,?,?> protocol = this.getContext().getProtocol();
            // @FIXME: refactor due to Protocols API refactoring
//            IRequestsProcessor processor = protocol.createRequestsProcessor( this.getContext() );
//            processor.setServerContext(this.getContext());
            while ( this.isValid() ) {
                log.info("Waiting request...");
                try{
                    IRequest request = protocol.readRequest( socket.getInputStream() );
                    if ( request == null ) {
                        this.incrErrorsTick();
                        continue;
                    }

                    request.setSocket( socket );

                    log.info("Processing request...");
                    //@FIXME: due to Protocols API refactoring
                    // processor.onRequest(request);
                } catch ( Throwable e ) {
                    this.incrErrorsTick();
                } 
            }
        } catch ( Throwable e ) {
            log.error("Internal exception", e );
        }
        
        return true;
    }

}
