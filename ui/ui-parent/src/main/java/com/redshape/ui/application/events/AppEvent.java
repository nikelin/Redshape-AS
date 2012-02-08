package com.redshape.ui.application.events;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class AppEvent {
    private EventType type;
    private Object[] args;

    public AppEvent() {
    	this(null);
    }
    
    public AppEvent( EventType type ) {
        this(type, new Object[] {} );
    }

    public AppEvent( EventType type, Object... args ) {
        this.type = type;
        this.args = args;
    }

    public EventType getType() {
        return type;
    }

    public Object[] getArgs() {
        return args;
    }

    @SuppressWarnings("unchecked")
	public <V> V getArg( int num ) {
        return num >= this.args.length || num < 0 ? null : (V) this.args[num];
    }

    public boolean isSame( EventType type ) {
        return this.getType() != null ? this.getType().equals( type ) : false;
    }

    public boolean isSame( AppEvent event ) {
        return this.getType() != null ? this.getType().equals( event.getType() ) : false;
    }
}
