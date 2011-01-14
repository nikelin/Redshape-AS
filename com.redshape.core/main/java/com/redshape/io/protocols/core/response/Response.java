package com.redshape.io.protocols.core.response;

import com.redshape.io.protocols.core.request.RequestHeader;
import com.redshape.io.protocols.vanilla.renderers.JSONResponseRenderer;
import com.redshape.io.protocols.core.renderers.ResponseRenderer;
import com.redshape.render.RendererException;

import java.util.*;


/**
 * @author nikelin
 */
public class Response implements IResponse {
    private static final ResponseRenderer DEFAULT_RENDERER = new JSONResponseRenderer();

    private ResponseRenderer renderer;
    private Map<String, Object> params = new HashMap<String, Object>();
    private Collection<RequestHeader> headers = new HashSet<RequestHeader>();
    private Set<Object> errors = new HashSet<Object>();
    private String id;

    public Response() {
        this(null);
    }

    public Response( String id ) {
        this( id, DEFAULT_RENDERER);
    }

    public Response( String id, ResponseRenderer renderer) {
        this.id = id;
        this.renderer = renderer;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Response setId( String id ) {
        this.id = id;
        return this;
    }

    @Override
    public Response addParam( String name, Object value ) {
        this.params.put( name, value );
        return this;
    }

    @Override
    public boolean hasParam( String name ) {
        return this.params.containsKey(name);
    }

    @Override
    public Object getParam( String name) {
        return this.params.get(name);
    }

    @Override
    public Map<String, Object> getParams() {
        return this.params;
    }

    @Override
    public Response addError( Object error ) {
        this.errors.add( error );
        return this;
    }

    @Override
    public Set<Object> getErrors() {
        return this.errors;
    }

    @Override
    public Response addErrors( List<String> errors ) {
        this.errors.addAll( errors );
        return this;
    }

    @Override
    public Response addErrors( String[] errors ) {
        this.errors.addAll( Arrays.asList(errors) );
        return this;
    }

    protected ResponseRenderer getRenderer() {
        return this.renderer;
    }

    public Collection<RequestHeader> getHeaders() {
        return this.headers;
    }

    public void addHeader( RequestHeader header ) {
        this.headers.add(header);
    }

    public void setHeaders( Collection<RequestHeader> headers ) {
        this.headers = headers;
    }

    @Override
    public String toString() {
       try {
            return this.getRenderer().render( this ).toString();
       } catch ( RendererException e ) {
           return null;
       }
    }
}
