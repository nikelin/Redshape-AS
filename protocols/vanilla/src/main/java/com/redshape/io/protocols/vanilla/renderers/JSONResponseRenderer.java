package com.redshape.io.protocols.vanilla.renderers;

import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.core.renderers.IResponseRenderer;
import com.redshape.renderer.RendererException;
import com.redshape.renderer.json.JSONRenderer;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @Refactoring Move to render
 */
public class JSONResponseRenderer<T extends IResponse> extends JSONRenderer<T> implements IResponseRenderer<String, T> {
    private static final Logger log = Logger.getLogger( JSONResponseRenderer.class );

    public String render( Throwable e ) {
        return this.renderValue(e);
    }

    @Override
    public String render( T response ) throws RendererException {
        return this.renderValue( this.renderMap( response ) );
    }

    @Override
    public byte[] renderBytes( Throwable exception ) throws RendererException {
        return this.render(exception).getBytes();
    }

    @Override
    public byte[] renderBytes( Collection<T> responses ) throws RendererException {
        return this.render(responses).toString().getBytes();
    }

    @Override
    public byte[] renderBytes( T response ) throws RendererException {
        return this.render(response).getBytes();
    }

    @Override
    public String render( Collection<T> responses ) throws RendererException {
        List<Object> objects = new ArrayList<Object>();
        for ( T response : responses ) {
            objects.add( this.renderMap( response ) );
        }

        return this.renderValue( objects );
    }

    public Map<String, Object> renderMap( T response ) {
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
