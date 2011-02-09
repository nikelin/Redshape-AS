package com.redshape.io.protocols.dispatchers;

public class DispatcherException extends Exception {
	
	public DispatcherException() {
		super();
	}
	
	public DispatcherException( String message ) {
		super(message);
	}
	
	public DispatcherException( String message, Throwable exception ) {
		super(message, exception);
	}
	
}
