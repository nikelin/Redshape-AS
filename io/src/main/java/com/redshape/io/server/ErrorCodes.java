package com.redshape.io.server;

public class ErrorCodes extends Exception {
	private static final long serialVersionUID = -5071200945378934933L;

	protected ErrorCodes( String message ) {
		super(message);
	}
	
	public ErrorCodes( ErrorCodes code, Throwable e ) {
		super( code.getMessage(), e );
	}
	
	public static final ErrorCodes EXCEPTION_WRONG_REQUEST = new ErrorCodes( "Error.WrongRequest" );
	
	public static final ErrorCodes EXCEPTION_INTERNAL = new ErrorCodes( "Error.ExceptionInternal" );
	
	public static final ErrorCodes EXCEPTION_RECONNECT = new ErrorCodes( "Error.ExceptionReconnect" );

	public static final ErrorCodes EXCEPTION_MISSED_REQUEST_BODY = new ErrorCodes( "Error.MissedRequestBody" );

	public static final ErrorCodes EXCEPTION_WRONG_REQUEST_HEADERS = new ErrorCodes( "Error.WrongRequestHeaders" );

	public static final ErrorCodes EXCEPTION_MISSED_REQUEST_HEAD = new ErrorCodes( "Error.MissedRequestHead" );

}
