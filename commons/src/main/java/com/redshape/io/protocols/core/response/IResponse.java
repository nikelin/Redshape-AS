package com.redshape.io.protocols.core.response;

import com.redshape.io.protocols.core.request.RequestHeader;
import com.redshape.renderer.Renderable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:53:08 AM
 * To change this template use File | Settings | File Templates.
 */
@Renderable
public interface IResponse {
    public String getId();

    public Response setId( String id );

    public Response addParam( String name, Object value );

    public boolean hasParam( String name );

    public Object getParam( String name);

    public Map<String, Object> getParams();

    public Collection<RequestHeader> getHeaders();

    public void setHeaders( Collection<RequestHeader> headers );

    public void addHeader( RequestHeader header );

    public Response addError( Object error );

    public Set<Object> getErrors();

    public Response addErrors( List<String> errors );

    public Response addErrors( String[] errors );

    public String toString();
}
