package com.redshape.bindings;

public class BindingException extends Exception {
	private static final long serialVersionUID = 1466614284326494199L;

	public BindingException() {
		this(null);
	}
	
	public BindingException( String message ) {
		this(message, null);
	}
	
	public BindingException( String message, Throwable e ) {
		super(message, e);
	}
	
}
