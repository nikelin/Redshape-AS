package com.vio.server;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.response.IResponse;
import com.vio.io.protocols.core.sources.output.BufferedOutput;
import com.vio.exceptions.ErrorCode;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.adapters.socket.server.IServerSocketAdapter;
import com.vio.server.adapters.socket.SocketAdapterFactory;
import com.vio.server.listeners.connection.IConnectionListener;
import com.vio.server.listeners.request.IRequestListener;
import com.vio.server.listeners.IRequestsProcessor;
import com.vio.server.policy.IPolicy;
import com.vio.server.policy.PolicyType;
import com.vio.utils.Constants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.server
 * @date Apr 14, 2010
 */
public abstract class AbstractSocketServer<T extends IProtocol, D extends IDispatcher, R extends IResponse>
                extends AbstractServer implements ISocketServer<T, D, R> {
    final private static Logger log = Logger.getLogger( ApplicationServer.class );

    public static int EXPIRATION_TIME = Constants.TIME_SECOND * 1000;
    public static int CONNECTION_KEEP_ALIVE = Constants.TIME_SECOND * 25;

    /**
     * Объект адаптера для работы с запросами поступающими на сокет
     *
     * @var SocketAdapter
     */
    private IServerSocketAdapter socket;

    private T protocol;

    /**
     * Основной контроллер запросов; выполняет предобработку после которой
     * передаёт выполнение конкретному, связанному с текущим запросом, контроллеру
     */
    private D dispatcher;

    private IRequestListener requestListener;

    private IRequestsProcessor requestsProcessor;

    private IConnectionListener connectionListener;

    private Map<ThreadGroup, ISocketAdapter> connections = new HashMap<ThreadGroup, ISocketAdapter>();

    private Class<? extends IRequestListener> requestsListener;

    private Map<Class<? extends IPolicy>, ? extends IPolicy> policies = new HashMap();

    public AbstractSocketServer(
            String host,
            Integer port,
            Boolean isSSLEnabled,
            T protocol ) throws ServerException
    {
        super( host, port, isSSLEnabled );

        this.protocol = protocol;
    }

    @Override
    public void initialize() throws ServerException {
        this.socket = this.createServerSocket();

        this.markInitialized( true );
    }

    public IServerSocketAdapter getSocketAdapter() {
        return this.socket;
    }

    public void startListen() throws ServerException, IOException {
        if ( !this.isInitialized() ) {
            this.initialize();
        }

        if ( this.isRunning() ) {
            throw new ServerException( ErrorCode.EXCEPTION_SERVER_ALREADY_STARTED);
        }  else {
            this.changeState(ServerState.RUNNING);
        }

        this.getSocketAdapter().startListening();

        this._startListen();
    }

    private void _startListen() throws ServerException {
        for ( ; ;  ) {
            try {
                ISocketAdapter socket = this.getSocketAdapter().accept();

                this.setLocalSocket(socket);

                if ( this.checkPolicy( this.getProtocol().getClass(), PolicyType.ON_CONNECTION ) ) {
                    this.getConnectionListener().onConnection(socket);
                } else {
                    this.refuseConnection(socket);
                }
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
            }
        }
    }

    public void setConnectionListener( IConnectionListener listener ) {
        this.connectionListener = listener;
        listener.setServer(this);
    }

    public IConnectionListener getConnectionListener() {
        return this.connectionListener;
    }

    public void setRequestListener( IRequestListener listener ) {
        this.requestListener = listener;
        listener.setContext(this);
    }

    public IRequestListener getRequestListener() {
        return this.requestListener;
    }

    public void setRequestsProcessor( IRequestsProcessor processor ) {
        this.requestsProcessor = processor;
    }

    public IRequestsProcessor getRequestsProcessor() {
        return this.requestsProcessor;
    }

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

    public void setLocalSocket( ISocketAdapter socket ) {
        this.connections.put( Thread.currentThread().getThreadGroup(), socket );
    }

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

    public void shutdown() {
        try {
            this.getSocketAdapter().close();
        } catch ( Throwable e ) {
            log.info("Unable to shutdown underlying server socket...");
            log.error( e.getMessage(), e );
        }

        this.changeState( ServerState.DOWN );
    }

    public D getDispatcher() {
    	return this.dispatcher;
    }

    public void setDispatcher( D dispatcher ) {
    	this.dispatcher = dispatcher;
    }

    public void writeResponse( ISocketAdapter socket, R response ) throws ServerException {
        try {
            this.getProtocol().writeResponse( new BufferedOutput( socket.getOutputStream() ), response );
        } catch ( Throwable e ) {
            throw new ServerException( ErrorCode.EXCEPTION_INTERNAL );
        }
    }

    public void writeResponse( ISocketAdapter socket, ExceptionWithCode exception ) {
        try {
            this.getProtocol().writeResponse( new BufferedOutput(socket.getOutputStream()), exception );
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            log.info("Cannot write exception!");
        }
    }

    public void writeResponse( ISocketAdapter socket, Collection<R> response ) throws ServerException {
        try {
            this.getProtocol().writeResponse( new BufferedOutput( socket.getOutputStream() ), response );
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            throw new ServerException( ErrorCode.EXCEPTION_INTERNAL );
        }
    }

    public void refuseConnection() throws ServerException {
        this.refuseConnection( this.getLocalSocket() );
    }

    public void refuseConnection( ISocketAdapter socket ) throws ServerException {
        try {
            this.getProtocol().writeResponse( new BufferedOutput( socket.getOutputStream() ), new ServerException( ErrorCode.EXCEPTION_RECONNECT ) );

            socket.close();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServerException();
        }
    }

    public IRequestListener createRequestListener(  ) throws ServerException {
        try {
            IRequestListener listener = this.requestsListener.newInstance();
            listener.setContext( this );

            return listener;
        } catch ( Throwable e ) {
            throw new ServerException();
        }
    }

    public void setRequestListener( Class<? extends IRequestListener> listener ) {
        this.requestsListener = listener;
    }

    public void setProtocol( T protocol ) {
        this.protocol = protocol;
    }

    public T getProtocol() {
        return this.protocol;
    }

}