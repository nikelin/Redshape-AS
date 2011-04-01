package com.redshape.server;

import com.redshape.io.net.adapters.socket.SocketAdapterFactory;
import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.net.adapters.socket.server.IServerSocketAdapter;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.response.IResponse;

import com.redshape.io.server.ErrorCodes;

import com.redshape.io.server.ServerException;
import com.redshape.io.server.ServerState;
import com.redshape.io.server.policy.IPolicy;
import com.redshape.server.execution.ServerExecutionThread;
import com.redshape.utils.Constants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nikelin
 * @project vio
 * @package com.vio.server
 * @date Apr 14, 2010
 */
public abstract class AbstractSocketServer<T extends IProtocol<?, ?,?,?,?,R>, R extends IResponse, V>
                extends AbstractServer<T,V> implements ISocketServer<T, R, V> {
    final private static Logger log = Logger.getLogger( ApplicationServer.class );

    public static int DEFAULT_THREADS_COUNT = 100;
    public static int EXPIRATION_TIME = Constants.TIME_SECOND * 1000;
    public static int CONNECTION_KEEP_ALIVE = Constants.TIME_SECOND * 25;

    /**
     * Объект адаптера для работы с запросами поступающими на сокет
     *
     * @var SocketAdapter
     */
    private IServerSocketAdapter socket;

    private T protocol;
    
    private Integer threadsCount = DEFAULT_THREADS_COUNT;
    
    private ExecutorService executor;

    private Map<ThreadGroup, ISocketAdapter> connections = new HashMap<ThreadGroup, ISocketAdapter>();
    private Map<Class<? extends IPolicy<V>>, IPolicy<V>> policies = new HashMap<Class<? extends IPolicy<V>>, IPolicy<V>>();

    public AbstractSocketServer(
            String host,
            Integer port,
            Boolean isSSLEnabled,
            T protocol ) throws ServerException
    {
        super( host, port, isSSLEnabled );

        this.protocol = protocol;
        this.executor = Executors.newFixedThreadPool( this.threadsCount );
        
    }

    @Override
    public void initialize() throws ServerException {
        this.socket = this.createServerSocket();

        for ( int  i = 0; i < this.threadsCount; i++ ) {
        	this.executor.execute( new ServerExecutionThread<T>(this) );
        }
        
        this.markInitialized( true );
    }

    @Override
    public IServerSocketAdapter getSocket() {
        return this.socket;
    }

    @Override
    public void startListen() throws ServerException, IOException {
        if ( !this.isInitialized() ) {
            this.initialize();
        }

        if ( this.isRunning() ) {
            throw new ServerException("ErrorCode.EXCEPTION_SERVER_ALREADY_STARTED");
        }  else {
            this.changeState(ServerState.RUNNING);
        }
        
        this.getSocket().startListening();
    }

    @Override
    public ISocketAdapter getLocalSocket() {
        ISocketAdapter adapter = this.connections.get( Thread.currentThread().getThreadGroup() );
        if ( adapter != null ) {
            return adapter;
        }

        ThreadGroup parent = Thread.currentThread().getThreadGroup();
        while ( adapter == null && ( parent = parent.getParent() ) != null ) {
            adapter = this.connections.get(parent);
        }

        return adapter;
    }

    @Override
    public void setLocalSocket( ISocketAdapter socket ) {
        this.connections.put( Thread.currentThread().getThreadGroup(), socket );
    }

    @Override
    public void closeLocalSocket() {
        this.connections.remove( Thread.currentThread() );
    }

    /**
     * Фабричный метод для создания серверного сокета
     * @return ServerSocket
     * @throws IOException
     */
    protected IServerSocketAdapter createServerSocket() throws ServerException {
        try {
            SocketAdapterFactory factory = SocketAdapterFactory.getDefault();
            IServerSocketAdapter adapter;
            if ( this.isSSLEnabled() ) {
                log.info("Creating SSL Socket on " + this.getHost() + ":" + this.getPort() );
                adapter = factory.createSSLServerSocketAdapter( this.getHost(), this.getPort() );
            } else {
                log.info("Started in unsecure mode...");
                adapter = factory.createServerSocketAdapter( this.getHost(), this.getPort() );
            }

            return adapter;
        } catch ( IOException e ) {
            throw new ServerException();
        }
    }

    @Override
    public void shutdown() {
        try {
            this.getSocket().close();
        } catch ( Throwable e ) {
            log.info("Unable to shutdown underlying server socket...");
            log.error( e.getMessage(), e );
        }

        this.changeState( ServerState.DOWN );
    }

    @Override
    public void writeResponse( ISocketAdapter socket, R response ) throws ServerException {
        try {
            this.getProtocol().writeResponse( socket.getOutputStream(), response );
        } catch ( Throwable e ) {
            throw new ServerException( ErrorCodes.EXCEPTION_INTERNAL );
        }
    }

    @Override
    public void writeResponse( ISocketAdapter socket, Throwable exception ) {
        try {
            this.getProtocol().writeResponse( socket.getOutputStream(), exception );
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            log.info("Cannot write exception!");
        }
    }

    @Override
    public void writeResponse( ISocketAdapter socket, Collection<R> response ) throws ServerException {
        try {
            this.getProtocol().writeResponse( socket.getOutputStream(), response );
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            throw new ServerException( ErrorCodes.EXCEPTION_INTERNAL );
        }
    }

    public void refuseConnection() throws ServerException {
        this.refuseConnection( this.getLocalSocket() );
    }

    @Override
    public void refuseConnection( ISocketAdapter socket ) throws ServerException {
        try {
            this.getProtocol().writeResponse( socket.getOutputStream(), new ServerException( ErrorCodes.EXCEPTION_RECONNECT ) );

            socket.close();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServerException();
        }
    }

    @Override
    public void setProtocol( T protocol ) {
        this.protocol = protocol;
    }

    @Override
    public T getProtocol() {
        return this.protocol;
    }
}
