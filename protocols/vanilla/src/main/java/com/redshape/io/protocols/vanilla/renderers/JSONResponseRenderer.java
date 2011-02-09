package com.redshape.io.protocols.vanilla.renderers;

import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.io.protocols.core.renderers.ResponseRenderer;
import com.redshape.renderer.RendererException;
import com.redshape.renderer.json.JSONRenderer;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @Refactoring Move to render
 */
public class JSONResponseRenderer extends JSONRenderer<IResponse> implements ResponseRenderer<String, IResponse> {
    private static final Logger log = Logger.getLogger( JSONResponseRenderer.class );

    public String render( ExceptionWithCode e ) {
        return this.renderValue(e);
    }

    @Override
    public String render( IResponse response ) throws RendererException {
        return this.renderValue( this.renderMap( response ) );
    }

    @Override
    public byte[] renderBytes( ExceptionWithCode exception ) throws RendererException {
        return this.render(exception).getBytes();
    }

    @Override
    public byte[] renderBytes( Collection<IResponse> responses ) throws RendererException {
        return this.render(responses).toString().getBytes();
    }

    @Override
    public byte[] renderBytes( IResponse response ) throws RendererException {
        return this.render(response).getBytes();
    }

    @Override
    public String render( Collection<IResponse> responses ) throws RendererException {
        List<Object> objects = new ArrayList<Object>();
        for ( IResponse response : responses ) {
            objects.add( this.renderMap( response ) );
        }

        return this.renderValue( objects );
    }

    public Map<String, Object> renderMap( IResponse response ) {
    	Map<String, Object> result = new HashMap<String, Object>();

        result.put( "id", response.getId() );

        if ( !response.getErrors().isEmpty() ) {
        	result.put("error", true);

            log.info( String.valueOf( response.getErrors() ) );
            result.put("errors", response.getErrors() );
        }

        result.put("params", response.getParams() );

        return result;
    }

}
