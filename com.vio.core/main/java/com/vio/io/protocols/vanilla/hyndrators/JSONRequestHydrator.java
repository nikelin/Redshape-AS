package com.vio.io.protocols.vanilla.hyndrators;

import com.vio.io.protocols.core.request.RequestException;
import com.vio.io.protocols.core.request.RequestHeader;
import com.vio.io.protocols.vanilla.request.InterfaceInvoke;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author nikelin
 */
public class JSONRequestHydrator implements IApiRequestHydrator {
    private static final Logger log = Logger.getLogger( JSONRequestHydrator.class );

    private String data;

    public JSONRequestHydrator() {}

    public JSONRequestHydrator( String data ) throws RequestException {
        this.data = data;

        this.parse(data);
    }

    @Override
    public List<RequestHeader> readHeaders() {
        if ( !this.getObject().containsKey("headers") ) {
            return null;
        }

        List<RequestHeader> headers = new ArrayList<RequestHeader>();
        Map<String, Object> requestHeaders = this.convertJSONObjectToMap( jsonObject.getJSONObject("headers") ); 
        for ( String key : requestHeaders.keySet() ) {
            headers.add( new RequestHeader( key, requestHeaders.get( key ) ) );
        }

        return headers;
    }

    @Override
    public void parse( String data ) {
        this.jsonObject = (JSONObject) JSONSerializer.toJSON( data.toString() );
    }

    @Override
    public Collection<IAPIRequest> readBody() {
        Collection<IAPIRequest> result = new HashSet<IAPIRequest>();

        if ( this.getObject().containsKey("body") ) {
            Object body = this.getObject().get("body");

            try {
                Object mInvokeResult = this.getClass().getMethod("buildBody", body.getClass() ).invoke( this, body);
                if ( IAPIRequest.class.isInstance(mInvokeResult) ) {
                    result.add( (IAPIRequest) mInvokeResult );
                } else {
                    result = (Collection<IAPIRequest>) mInvokeResult;
                }
            } catch ( Throwable e ) {
                log.info( body.getClass().getName() );
                log.error( e.getMessage(), e );
            }
        }

        return result;
    }

    public List<IAPIRequest> buildBody( JSONArray body ) {
        List<IAPIRequest> result = new ArrayList<IAPIRequest>();

        for ( int item = 0; item < body.size(); item++ ) {
            Object ob = body.get( item );

            if ( ob.getClass().isAssignableFrom( JSONObject.class ) ) {
                result.add( this.buildBody( (JSONObject) ob ) );
            }
        }

        return result;
    }

    public IAPIRequest buildBody( JSONObject body ) {
        InterfaceInvoke invoke = new InterfaceInvoke();

        invoke.setId( body.containsKey("id") ? body.get("id").toString() : null );
        invoke.setFeature(  body.containsKey("interface") ? (String) body.get("interface") : null );
        invoke.setAspectName( body.containsKey("action") ? (String) body.get("action") : null );

        if ( body.containsKey("params") && body.get("params").getClass() == JSONObject.class ) {
            invoke.setParams( this.convertJSONObjectToMap( body.getJSONObject("params") ) );
        }

        return invoke;
    }

    protected JSONObject getObject( ) {
        return this.jsonObject;
    }

    protected Map<String, Object> convertJSONObjectToMap( JSONObject object ) {
        Map<String, Object> result = new HashMap<String, Object>();

        for ( Object key : object.keySet() ) {
            Object value = object.get( key );

            if (  value.getClass() == JSONObject.class ) {
                value = this.convertJSONObjectToMap( (JSONObject) value );
            } else if ( value.getClass() == JSONArray.class ) {
                value = Arrays.asList(  ( (JSONArray) value ).toArray() );
            }  else {
                String val = String.valueOf(value);

                try {
                    value = Integer.valueOf( val );
                } catch ( NumberFormatException e ) {
                    if ( val.equals( "true" ) || val.equals( "false" ) ) {
                        value = Boolean.parseBoolean(val);
                    }  else {
                        value = String.valueOf(value);
                    }
                }
            }
            
            result.put( String.valueOf(key), value );
        }

        return result;
    }

    private JSONObject jsonObject;
}

