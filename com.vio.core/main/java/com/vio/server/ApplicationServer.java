package com.vio.server;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.response.IResponse;
import com.vio.io.protocols.vanilla.response.ApiResponse;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.server.listeners.connection.ConnectionsListener;
import com.vio.server.listeners.connection.IConnectionListener;
import com.vio.server.listeners.request.IRequestListener;
import com.vio.server.listeners.IRequestsProcessor;
import com.vio.server.listeners.request.ApiRequestListener;
import org.apache.log4j.Logger;

/**
 * Сервер обработки запросов
 *
 * @author nikelin
 */
public class ApplicationServer extends AbstractSocketServer<IProtocol, IDispatcher, IResponse> {
    private static final Logger log = Logger.getLogger( ApplicationServer.class );

    public final static Class<? extends IApiResponse> DEFAULT_RESPONSE_OBJECT = ApiResponse.class;

    public ApplicationServer() throws ServerException  {
        this( null, null, null );
    }

    public ApplicationServer( String host, Integer port, Boolean isSSLEnabled ) throws ServerException {
        this( host, port, isSSLEnabled, SocketServerFactory.DEFAULT_PROTOCOL );
    }

    public ApplicationServer( String host, Integer port, Boolean isSSLEnabled, IProtocol protocol ) throws ServerException {
       super( host, port, isSSLEnabled, protocol );
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