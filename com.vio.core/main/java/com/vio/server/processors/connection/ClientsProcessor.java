package com.vio.server.processors.connection;

import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.ProtocolException;
import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.server.ISocketServer;
import com.vio.server.BalanserOverloadedException;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.processors.request.IRequestsProcessor;
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
    private ISocketServer server;

    public static int MAX_WORKER_WARNS = 12;

    public ISocketServer getContext() {
        return this.server;
    }

    @Override
    public void setContext( ISocketServer server ) {
        this.server = server;
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

        Thread thread = new Thread( Thread.currentThread().getThreadGroup(), new WorkerThread( this.getContext(), socket), "Connection processing thread" );
        thread.start();

        return true;
    }

    private final class WorkerThread implements Runnable {
        private int errorsTick;
        private boolean isValid = true;
        private ISocketAdapter client;
        private ISocketServer server;

        public WorkerThread( ISocketServer server, ISocketAdapter client ) {
            this.server = server;
            this.client = client;
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

        @Override
        public void run() {
            try {
                IProtocol protocol = this.server.getProtocol();
                IRequestsProcessor processor = protocol.createRequestsProcessor();
                processor.setServerContext(this.server);
                
                BufferedInput input = new BufferedInput( this.client.getInputStream() );
                while ( this.isValid() ) {
                    log.info("Waiting request...");
                    try{
                        IRequest request = protocol.readRequest( input );
                        if ( request == null ) {
                            this.incrErrorsTick();
                            continue;
                        }

                        request.setSocket( this.client );

                        log.info("Processing request...");
                        processor.onRequest(request);
                    } catch ( ExceptionWithCode e ) {
                        this.server.writeResponse( this.client, e );
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
        }
    }
}
