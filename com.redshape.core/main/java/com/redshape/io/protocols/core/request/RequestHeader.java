package com.redshape.io.protocols.core.request;

import com.redshape.render.IRenderable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 24, 2010
 * Time: 7:46:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestHeader implements IRenderable {
    private String name;
    private Object value;

    public RequestHeader( String name, Object value ) {
        this.name = name;
        this.value = value;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setValue( Object value ) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

}
