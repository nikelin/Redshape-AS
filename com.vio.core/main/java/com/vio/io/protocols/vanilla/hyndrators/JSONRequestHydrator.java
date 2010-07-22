package com.vio.io.protocols.vanilla.hyndrators;

import com.vio.io.protocols.vanilla.request.InterfaceInvocation;
import com.vio.io.protocols.request.RequestException;
import com.vio.io.protocols.request.RequestHeader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author nikelin
 */
public class JSONRequestHydrator implements ApiRequestHydrator {
    private static final Logger log = Logger.getLogger( JSONRequestHydrator.class );

    private String data;

    public JSONRequestHydrator() {}

    public JSONRequestHydrator( String data ) throws RequestException {
        this.data = data;

        this.parse(data);
    }

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

    public void parse( String data ) {
        this.jsonObject = (JSONObject) JSONSerializer.toJSON( data.toString() );
    }

    public List<InterfaceInvocation> readBody() {
        List<InterfaceInvocation> result = new ArrayList<InterfaceInvocation>();

        if ( this.getObject().containsKey("body") ) {
            Object body = this.getObject().get("body");

            try {
                Object mInvokeResult = this.getClass().getMethod("buildBody", body.getClass() ).invoke( this, body);
                if ( InterfaceInvocation.class.isInstance(mInvokeResult) ) {
                    result.add( (InterfaceInvocation) mInvokeResult );
                } else {
                    result = (List<InterfaceInvocation>) mInvokeResult;
                }
            } catch ( Throwable e ) {
                log.info( body.getClass().getName() );
                log.error( e.getMessage(), e );
            }
        }

        return result;
    }

    public List<InterfaceInvocation> buildBody( JSONArray body ) {
        List<InterfaceInvocation> result = new ArrayList<InterfaceInvocation>();

        for ( int item = 0; item < body.size(); item++ ) {
            Object ob = body.get( item );

            if ( ob.getClass().isAssignableFrom( JSONObject.class ) ) {
                result.add( this.buildBody( (JSONObject) ob ) );
            }
        }

        return result;
    }

    public InterfaceInvocation buildBody( JSONObject body ) {
        InterfaceInvocation invoke = new InterfaceInvocation();

        invoke.setId( body.containsKey("id") ? body.get("id").toString() : null );
        invoke.setInterfaceId(  body.containsKey("interface") ? (String) body.get("interface") : null );
        invoke.setAction( body.containsKey("action") ? (String) body.get("action") : null );

        if(body.containsKey("waiting"))
            invoke.setResponce( body.getJSONObject("waiting") );
        
        /**
         * Хук для предотвращения ошибки пустого объекта, который PHP кодирует как пустой массив
         */
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

