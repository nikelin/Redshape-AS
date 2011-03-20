package com.redshape.ui.events;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public class EventType {
    private String code;

    protected EventType( String code ) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public boolean equals( Object type ) {
        if ( type instanceof EventType ) {
            return this.getCode().equals( ( (EventType) type ).getCode() );
        } else {
            return super.equals(type);
        }
    }

    public static EventType forCode( String code ) {
        return new EventType(code);
    }

}
