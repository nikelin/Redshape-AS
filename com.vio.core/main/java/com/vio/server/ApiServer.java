package com.vio.server;

import com.vio.io.protocols.vanilla.hyndrators.ApiRequestHydrator;
import com.vio.io.protocols.vanilla.hyndrators.JSONRequestHydrator;
import com.vio.io.protocols.vanilla.response.ApiResponse;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.io.protocols.readers.IRequestReader;
import com.vio.io.protocols.vanilla.readers.APIRequestReader;
import com.vio.io.protocols.vanilla.renderers.JSONResponseRenderer;
import com.vio.io.protocols.renderers.ResponseRenderer;
import com.vio.io.protocols.writers.IResponseWriter;
import com.vio.io.protocols.writers.ResponseWriter;
import com.vio.server.listeners.ConnectionsListener;
import com.vio.server.listeners.IConnectionListener;
import com.vio.server.listeners.IRequestListener;
import com.vio.server.listeners.IRequestsProcessor;
import com.vio.server.listeners.api.ApiRequestListener;
import org.apache.log4j.Logger;

/**
 * Сервер обработки запросов
 *
 * @author nikelin
 */
public class ApiServer extends AbstractSocketServer<ApiRequestHydrator, ResponseRenderer, IApiResponse> {
    private static final Logger log = Logger.getLogger( ApiServer.class );

    public final static ResponseRenderer DEFAULT_RESPONSE_RENDERER = new JSONResponseRenderer();

    public final static ApiRequestHydrator DEFAULT_REQUEST_HYDRATOR = new JSONRequestHydrator();

    public final static Class<? extends IRequestsProcessor> DEFAULT_REQUESTS_PROCESSOR = ApiRequestListener.class;

    public final static Class<? extends IRequestListener> DEFAULT_REQUEST_LISTENER = ApiRequestListener.class;

    public final static Class<? extends IApiResponse> DEFAULT_RESPONSE_OBJECT = ApiResponse.class;

    public final static Class<? extends IConnectionListener> DEFAULT_CONNECTION_LISTENER = ConnectionsListener.class;

    /**
     * Дефолтная абстракция для преобразования объекта отклика к строке
     *
     * @var Writer
     */
    public final static IResponseWriter DEFAULT_RESPONSE_WRITER = new ResponseWriter( DEFAULT_RESPONSE_RENDERER );

    /**
     * Дефолтная абстракция для чтения запроса клиента
     *
     * @var Reader
     */
    public final static IRequestReader DEFAULT_REQUEST_READER = new APIRequestReader( DEFAULT_REQUEST_HYDRATOR );

    public ApiServer() throws ServerException  {
        this( null, null, null );
    }

    public ApiServer( String host, Integer port, Boolean isSSLEnabled ) throws ServerException {
       super( host, port, isSSLEnabled,
                          DEFAULT_RESPONSE_WRITER,
                          DEFAULT_REQUEST_READER,
                          DEFAULT_REQUEST_HYDRATOR,
                          DEFAULT_RESPONSE_RENDERER );

       try {
           this.setupDefaultListeners();
       } catch( Throwable e ) {
           log.error( e.getMessage(),e );
           throw new ServerException();
       }
    }

    private void setupDefaultListeners() throws ServerException {
        try {
            IConnectionListener listener = DEFAULT_CONNECTION_LISTENER.newInstance();
            listener.setServer(this);
            this.setConnectionListener( listener );

            this.setRequestListener( DEFAULT_REQUEST_LISTENER );
        } catch ( Throwable e ) {
            throw new ServerException();
        }
    }

    public IApiResponse createResponseObject() throws ServerException {
        try {
            return DEFAULT_RESPONSE_OBJECT.newInstance();
        } catch ( Throwable e ) {
            throw new ServerException();
        }
    }

    public boolean isPropertySupports( String name ) {
        return false;
    }

}