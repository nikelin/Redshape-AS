package com.redshape.features;

import com.redshape.exceptions.ExceptionWithCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 6:16:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class InteractionResult implements IInteractionResult {
    private Map<String, Object> attributes = new HashMap();
    private boolean is_error;
    private ExceptionWithCode last_exception;

    public void setAttribute( String name, Object value ) {
        this.attributes.put(name, value);
    }

    public Object getAttribute( String name ) {
        return this.attributes.get(name);
    }

    public boolean hasAttribute( String name ) {
        return this.attributes.containsKey(name);
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public ExceptionWithCode getError() {
        return this.last_exception;
    }

    public void markError( boolean state ) {
        this.is_error = state;
    }

    public boolean isError() {
        return this.is_error;
    }

}
