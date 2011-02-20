package com.redshape.server;

import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.ApiResponse;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.io.server.ServerException;

import org.apache.log4j.Logger;

/**
 * Сервер обработки запросов
 *
 * @author nikelin
 */
public class ApplicationServer extends AbstractSocketServer<IProtocol<?, ?,?,?,?,IResponse>, IResponse, IApiRequest> {
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

    @Override
    public IApiResponse createResponseObject() throws ServerException {
        try {
            return DEFAULT_RESPONSE_OBJECT.newInstance();
        } catch ( Throwable e ) {
            throw new ServerException();
        }
    }

    @Override
    public boolean isPropertySupports( String name ) {
        return false;
    }

}