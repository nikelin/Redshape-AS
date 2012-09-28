package com.redshape.servlet.core.format;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/10/12
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONFormatProcessor implements IRequestFormatProcessor {

    public static final String MARKER_HEADER = "XMLHttpRequest";

    @Override
    public SupportType check(IHttpRequest request) throws ProcessingException {
        try {
            if ( !request.isPost() ) {
                return  SupportType.NO;
            }

            String body = this.getBody(request);

            String requestedWith = request.getHeader("X-Requested-With");
            if ( requestedWith != null && requestedWith.equals( MARKER_HEADER) ) {
                return SupportType.SHOULD;
            }

            if ( body.startsWith("{")
                    && body.endsWith("}") ) {
                return SupportType.MAY;
            }

            return SupportType.NO;
        } catch ( IOException e ) {
            throw new ProcessingException( e.getMessage(), e );
        }
    }

    protected String getBody( IHttpRequest request ) throws IOException {
        String body = request.getBody();
        if ( body == null || body.isEmpty() ) {
            if ( request.getParameters().isEmpty() ) {
                return "";
            }

            for ( String key : request.getParameters().keySet() ) {
                if ( key == null ) {
                    continue;
                }

                if ( key.startsWith("{") && key.endsWith("}") ) {
                    body = key;
                    break;
                }
            }

            if ( body == null || body.isEmpty() ) {
                return "";
            }
        }

        return body;
    }

    protected JSONObject readJSONRequest( IHttpRequest request )
            throws IOException {
        String requestData = this.getBody(request);

        if ( requestData.isEmpty() ) {
            throw new IllegalArgumentException("Request is empty");
        }

        return this.readJSONRequest( requestData );
    }

    protected JSONObject readJSONRequest(String data) {
        return JSONObject.fromObject(data);
    }


    @Override
    public void process(IHttpRequest request) throws ProcessingException {
        try {
            JSONObject object = this.readJSONRequest( request );
            for ( Object key : object.keySet() ) {
                request.setParameter( String.valueOf( key ), object.get(key) );
            }
        } catch ( IOException e ) {
            throw new ProcessingException( e.getMessage(), e );
        }
    }
}
