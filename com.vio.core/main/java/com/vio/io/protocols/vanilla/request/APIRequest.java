package com.vio.io.protocols.vanilla.request;

import com.vio.io.protocols.vanilla.hyndrators.ApiRequestHydrator;
import com.vio.io.protocols.vanilla.hyndrators.JSONRequestHydrator;
import com.vio.io.protocols.request.RequestException;
import com.vio.io.protocols.request.RequestFormattingException;
import com.vio.io.protocols.request.RequestHeader;
import com.vio.io.protocols.request.RequestProcessingException;
import com.vio.exceptions.ErrorCode;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.render.Renderable;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author nikelin
 */
public class APIRequest implements IAPIRequest, Renderable {
    private static Logger log = Logger.getLogger( APIRequest.class );
    protected static ApiRequestHydrator defaultHydrator = new JSONRequestHydrator();

    private IRequester identity;
    private ISocketAdapter socket;
    private Collection<RequestHeader> headers = new HashSet<RequestHeader>();
    private Collection<InterfaceInvocation> body = new ArrayList<InterfaceInvocation>();

    public APIRequest() {}

    public static APIRequest buildRequest(String data) throws Throwable {
        return buildRequest( data, defaultHydrator);
    }

    /**
     * @TODO refactoring needs (constructors extraction) 
     */
    public static APIRequest buildRequest( String data, ApiRequestHydrator hydrator) throws RequestException {
        try {
            APIRequest request = new APIRequest();
            log.info("Input request: " + data );

            hydrator.parse(data);

            Collection<RequestHeader> headers = hydrator.readHeaders();
            if ( headers == null ) {
                throw new RequestFormattingException(ErrorCode.EXCEPTION_MISSED_REQUEST_HEAD );
            }
            request.setHeaders( headers );

            if ( !isValidHeaders(request) ) {
                throw new RequestProcessingException(ErrorCode.EXCEPTION_WRONG_REQUEST_HEADERS );
            }

            List<InterfaceInvocation> body = hydrator.readBody();
            if ( body == null ) {
                throw new RequestFormattingException(ErrorCode.EXCEPTION_MISSED_REQUEST_BODY );
            }

            if ( !isValidBody(body) ) {
                throw new RequestProcessingException(ErrorCode.EXCEPTION_WRONG_REQUEST_BODY );
            }
            request.setInvokes( body );

            return request;
        } catch ( RequestException e ) {
            log.error( e.getMessage(), e );

            throw e;
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            throw new RequestFormattingException(ErrorCode.EXCEPTION_WRONG_REQUEST);
        }
    }

    public void setHeaders( Collection<RequestHeader> headers ) {
        this.headers = headers;
    }

    public boolean hasHeader( String name ) {
        for ( RequestHeader header : this.headers ) {
            if ( header.getName().equals( name ) ) {
                return true;
            }
        }

        return false;
    }

    public Collection<RequestHeader> getHeaders() {
        return this.headers;
    }

    public RequestHeader getHeader( String name ) {
        for( RequestHeader header : this.headers ) {
            if ( header.getName().equals( name ) ) {
                return header;
            }
        }

        return null;
    }

    public void setInvokes( Collection<InterfaceInvocation> body ) {
        this.body = body;
    }

    public void setHeader( String name, Object value ) {
        this.getHeader(name).setValue(value);
    }

    public void addInvoke( InterfaceInvocation invoke ) {
        this.body.add( invoke );
    }

    public Collection<InterfaceInvocation> getInvokes() {
        return this.body;
    }

    public IRequester getIdentity() {
        return this.identity;    
    }

    public void setIdentity( IRequester identity ) {
        this.identity = identity;
    }

    public boolean isAsync() {
        return this.hasHeader("async") && (Boolean) this.getHeader("async").getValue();
    }

    public boolean isPersistent() {
        return this.hasHeader("persistent") && (Boolean) this.getHeader("persistent").getValue();
    }

    protected static boolean isValidHeaders( APIRequest request ) {
        return request.hasHeader("api_key");
    }

    protected static boolean isValidBody( List<InterfaceInvocation> invokes ) {
        for ( InterfaceInvocation invoke : invokes ) {
            if ( invoke.getInterfaceId() == null || invoke.getInterfaceId().isEmpty() ) {
                return false;
            }

            if ( invoke.getAction() == null || invoke.getAction().isEmpty() ) {
                return false;
            }
        }

        return true;
    }

    public void setSocket( ISocketAdapter socket ) {
        this.socket = socket;
    }

    public ISocketAdapter getSocket() {
        return this.socket;
    }

}

