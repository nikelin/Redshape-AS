package com.redshape.utils.events;

public class AbstractEvent implements IEvent {
	private static final long serialVersionUID = -8828874673088349336L;
	
	private Object[] arguments;
	
	public AbstractEvent() {
		this( new Object[] {} );
	}
	
	public AbstractEvent( Object... args ) {
		this.arguments = args;
	}
	
    @SuppressWarnings("unchecked")
	public <V> V[] getArgs() {
    	return (V[]) this.arguments;
    }

    public void setArgs( Object... args ) {
    	this.arguments = args;
    }

    @SuppressWarnings("unchecked")
	public <V> V getArg( int index ) {
    	if ( index >= this.arguments.length || index < 0 ) {
    		throw new IllegalArgumentException("Wrong argument index");
    	}
    	
    	return (V) this.arguments[index];
    }
	
}
