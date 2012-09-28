package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 8, 2010
 * Time: 12:38:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventObject {
    private Object id;
    private Object[] params;


    public EventObject( Object id, Object...params ) {
        this.id = id;
        this.params = params;
    }

    public Object getId() {
        return this.id;
    }

    public Object[] getParams() {
        return this.params;
    }

}
