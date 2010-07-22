package com.vio.io.protocols.response;

import com.vio.render.Renderable;

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
public interface IResponse extends Renderable {
    String getId();

    Response setId( String id );

    Response addParam( String name, Object value );

    boolean hasParam( String name );

    Object getParam( String name);

    Map<String, Object> getParams();

    Response addError( Object error );

    Set<Object> getErrors();

    Response addErrors( List<String> errors );

    Response addErrors( String[] errors );

    String toString();
}
