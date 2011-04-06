package com.redshape.ui;

public class UnhandledUIException extends RuntimeException {
	private static final long serialVersionUID = -3781541313391099300L;

	public UnhandledUIException() {
		super();
	}
	
	public UnhandledUIException( String message ) {
		super(message);
	}
	
	public UnhandledUIException( String message, Throwable e ) {
		super( message, e );
	}
	
}
