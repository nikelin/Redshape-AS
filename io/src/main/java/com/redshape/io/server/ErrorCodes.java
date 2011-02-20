package com.redshape.io.server;

public class ErrorCodes extends Exception {
	private static final long serialVersionUID = -5071200945378934933L;

	protected ErrorCodes( String message ) {
		super(message);
	}
	
	public ErrorCodes( ErrorCodes code, Throwable e ) {
		super( code.getMessage(), e );
	}
	
	public static ErrorCodes EXCEPTION_INTERNAL = new ErrorCodes( "Error.ExceptionInternal" );
	
	public static ErrorCodes EXCEPTION_RECONNECT = new ErrorCodes( "Error.ExceptionReconnect" );

}
