package com.vio.server.listeners;

import com.vio.exceptions.ErrorCode;
import com.vio.server.IServer;
import com.vio.server.ISocketServer;
import com.vio.server.BalanserOverloadedException;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Dec 23, 2009
 * Time: 11:34:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionsListener implements IConnectionListener {
    private static final Logger log = Logger.getLogger( com.vio.server.listeners.ConnectionsListener.class );

    private ISocketServer server;

    public void setServer( ISocketServer server ) {
        this.server = server;
    }

    /**
     * Поставить текущего пользователя в очередь
     * @param socket
     * @throws java.io.IOException
     * @throws BalanserOverloadedException
     */
     public boolean onConnection( final ISocketAdapter socket ) throws ServerException {
        log.info("Processing adapters: " + socket.getRemoteSocketAddress() );

        final IRequestListener listener = ConnectionsListener.this.server.createRequestListener();

        Thread thread = new Thread( Thread.currentThread().getThreadGroup(), "Requests listener") {
            public void run() {
                try {
                    listener.onConnection(socket);
                } catch ( ServerException e ) {
                    log.error( e.getMessage(), e );
                }
            }
        };

        thread.start();

        return true;
    }

}
