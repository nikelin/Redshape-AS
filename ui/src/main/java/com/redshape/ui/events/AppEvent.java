package com.redshape.ui.events;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class AppEvent<T extends EventType> {
    private T type;
    private Object[] args;

    public AppEvent( T type ) {
        this(type, null);
    }

    public AppEvent( T type, Object... args ) {
        this.type = type;
        this.args = args;
    }

    public EventType getType() {
        return type;
    }

    public Object[] getArgs() {
        return args;
    }

    public <V> V getArg( int num ) {
        return (V) args[num];
    }

    public boolean isSame( EventType type ) {
        return this.getType().equals( type );
    }

    public boolean isSame( AppEvent event ) {
        return this.getType().equals( event.getType() );
    }
}
