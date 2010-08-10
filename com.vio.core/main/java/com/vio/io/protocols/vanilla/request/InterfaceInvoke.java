package com.vio.io.protocols.vanilla.request;

import com.vio.io.protocols.vanilla.hyndrators.IApiRequestHydrator;
import com.vio.io.protocols.vanilla.hyndrators.JSONRequestHydrator;
import com.vio.io.protocols.core.request.RequestException;
import com.vio.io.protocols.core.request.RequestFormattingException;
import com.vio.io.protocols.core.request.RequestHeader;
import com.vio.io.protocols.core.request.RequestProcessingException;
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
public class InterfaceInvoke implements IAPIRequest {
    private static Logger log = Logger.getLogger( InterfaceInvoke.class );
    protected static IApiRequestHydrator defaultHydrator = new JSONRequestHydrator();

    private String id;
    private String interfaceId;
    private String action;
    private boolean is_valid;
    private Map<String, Object> params = new HashMap<String, Object>();
    private Map<String, Object> response = new HashMap<String, Object>();
    private IRequester identity;
    private ISocketAdapter socket;
    private IAPIRequest parent;
    private Collection<RequestHeader> headers = new HashSet<RequestHeader>();
    private Collection<IAPIRequest> childs = new ArrayList<IAPIRequest>();

    @Override
    public void setId( String id ) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setParent( IAPIRequest request ) {
        this.parent = request;
    }

    @Override
    public IAPIRequest getParent() {
        return this.parent;
    }

    @Override
    public void setAspectName( String action ) {
        this.action = action;
    }

    @Override
    public String getAspectName() {
        return this.action;
    }

    @Override
    public void setFeature( String interfaceId ) {
        this.interfaceId = interfaceId;
    }

    @Override
    public String getFeature() {
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

    public static InterfaceInvoke buildRequest(String data) throws Throwable {
        return buildRequest( data, defaultHydrator);
    }

    /**
     * @TODO refactoring needs (constructors extraction) 
     */
    public static InterfaceInvoke buildRequest( String data, IApiRequestHydrator hydrator) throws RequestException {
        try {
            InterfaceInvoke request = new InterfaceInvoke();
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

            Collection<IAPIRequest> body = hydrator.readBody();
            if ( body == null ) {
                throw new RequestFormattingException(ErrorCode.EXCEPTION_MISSED_REQUEST_BODY );
            }

            boolean isValidBody = true;
            for ( IAPIRequest invoke : body ) {
                if ( invoke.getFeature() == null || invoke.getFeature().isEmpty() ) {
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
    public void setChildren( Collection<IAPIRequest> body ) {
        this.childs = body;
    }

    @Override
    public void setHeader( String name, Object value ) {
        this.getHeader(name).setValue(value);
    }

    @Override
    public void addChild( IAPIRequest invoke ) {
        this.childs.add( invoke );
    }

    @Override
    public Collection<IAPIRequest> getChildren() {
        return this.childs;
    }

    @Override
    public boolean hasChilds() {
        return !this.childs.isEmpty();
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

    protected static boolean isValidHeaders( InterfaceInvoke request ) {
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

