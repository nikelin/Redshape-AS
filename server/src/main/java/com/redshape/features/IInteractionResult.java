package com.redshape.features;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 6:16:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IInteractionResult {

    public void setAttribute( String name, Object value );

    public Object getAttribute( String name );

    public Map<String, Object> getAttributes();

    public boolean isError();

    public void markError( boolean status );

    public Throwable getError();

}
