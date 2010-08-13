package com.vio.io.protocols.vanilla.request;

import com.vio.io.protocols.core.request.*;
import com.vio.io.protocols.vanilla.hyndrators.IApiRequestHydrator;
import com.vio.io.protocols.vanilla.hyndrators.JSONRequestHydrator;
import com.vio.exceptions.ErrorCode;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author nikelin
 */
public class ApiRequest implements IApiRequest {
    private static Logger log = Logger.getLogger( ApiRequest.class );
    protected static IApiRequestHydrator defaultHydrator = new JSONRequestHydrator();

    private String id;
    private String interfaceId;
    private String actionId;
    private boolean is_valid;
    private RequestType type;
    private Map<String, Object> params = new HashMap<String, Object>();
    private Map<String, Object> response = new HashMap<String, Object>();
    private IRequester identity;
    private ISocketAdapter socket;
    private IApiRequest parent;
    private Collection<RequestHeader> headers = new HashSet<RequestHeader>();
    private Collection<IApiRequest> children = new ArrayList<IApiRequest>();

    @Override
    public void setId( String id ) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public RequestType getType() {
        return this.type;
    }

    @Override
    public void setType( RequestType type ) {
        this.type = type;
    }

    @Override
    public void setParent( IApiRequest request ) {
        this.parent = request;
    }

    @Override
    public IApiRequest getParent() {
        return this.parent;
    }

    @Override
    public void setAspectName( String action ) {
        this.actionId = action;
    }

    @Override
    public String getAspectName() {
        return this.actionId;
    }

    @Override
    public void setFeatureName( String interfaceId ) {
        this.interfaceId = interfaceId;
    }

    @Override
    public String getFeatureName() {
        return this.interfaceId;
    }

    @Override
    public boolean isValid() {
        return this.is_valid;
    }

    @Override
    public void markInvalid( boolean state ) {
        this.is_valid = state;
    }

    @Override
    public void addParam( String name, Object value ) {
        this.params.put( name, value );
    }

    @Override
    public boolean hasParam( String name ) {
        return this.params.containsKey(name);
    }

    @Override
    public Map<String, Object> getParams() {
        return this.params;
    }

    @Override
    public Object getParam( String name ) {
        return this.params.get(name);
    }

    @Override
    public Map<String, Object> getMap( String name ) {
        return (Map<String, Object>) this.params.get(name);
    }

    @Override
    public List<Object> getList( String name ) {
        return (List<Object>) this.params.get(name);
    }

    @Override
    public String getString( String name ) {
        return this.params.get(name).getClass().equals(String.class) ? (String) this.params.get(name) : String.valueOf( this.params.get(name) );
    }

    @Override
    public Integer getInteger( String name ) {
        return Integer.parseInt( String.valueOf( this.params.get(name) ) );
    }

    @Override
    public void setParams( Map<String, Object> params ) {
        this.params = params;
    }

    public static ApiRequest buildRequest(String data) throws Throwable {
        return buildRequest( data, defaultHydrator);
    }

    /**
     * @TODO refactoring needs (constructors extraction) 
     */
    public static ApiRequest buildRequest( String data, IApiRequestHydrator hydrator) throws RequestException {
        try {
            ApiRequest request = new ApiRequest();
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

            Collection<IApiRequest> body = hydrator.readBody();
            if ( body == null ) {
                throw new RequestFormattingException(ErrorCode.EXCEPTION_MISSED_REQUEST_BODY );
            }

            boolean isValidBody = true;
            for ( IApiRequest invoke : body ) {
                if ( invoke.getFeatureName() == null || invoke.getFeatureName().isEmpty() ) {
                    isValidBody = false;
                    break;
                }

                if ( invoke.getAspectName() == null || invoke.getAspectName().isEmpty() ) {
                    isValidBody = false;
                    break;
                }

                request.addChild(invoke);
            }

            if ( !isValidBody ) {
                throw new RequestFormattingException( ErrorCode.EXCEPTION_MISSED_REQUEST_BODY );
            }

            return request;
        } catch ( RequestException e ) {
            throw e;
        } catch ( Throwable e ) {
            throw new RequestFormattingException(ErrorCode.EXCEPTION_WRONG_REQUEST);
        }
    }

    @Override
    public void setHeaders( Collection<RequestHeader> headers ) {
        this.headers = headers;
    }

    @Override
    public boolean hasHeader( String name ) {
        for ( RequestHeader header : this.headers ) {
            if ( header.getName().equals( name ) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Collection<RequestHeader> getHeaders() {
        return this.headers;
    }

    @Override
    public RequestHeader getHeader( String name ) {
        for( RequestHeader header : this.headers ) {
            if ( header.getName().equals( name ) ) {
                return header;
            }
        }

        return null;
    }

    @Override
    public void setChildren( Collection<IApiRequest> body ) {
        this.children = body;
    }

    @Override
    public void setHeader( String name, Object value ) {
        this.getHeader(name).setValue(value);
    }

    @Override
    public void addChild( IApiRequest invoke ) {
        this.children.add( invoke );
    }

    @Override
    public Collection<IApiRequest> getChildren() {
        return this.children;
    }

    @Override
    public boolean hasChilds() {
        return !this.children.isEmpty();
    }

    @Override
    public IRequester getIdentity() {
        return this.identity;    
    }

    @Override
    public void setIdentity( IRequester identity ) {
        this.identity = identity;
    }

    @Override
    public boolean isAsync() {
        return this.hasHeader("async") && (Boolean) this.getHeader("async").getValue();
    }

    protected static boolean isValidHeaders( ApiRequest request ) {
        return request.hasHeader("api_key");
    }

    @Override
    public void setSocket( ISocketAdapter socket ) {
        this.socket = socket;
    }

    @Override
    public ISocketAdapter getSocket() {
        return this.socket;
    }

}

