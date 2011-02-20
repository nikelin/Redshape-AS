package com.redshape.api;

public class ErrorCodes extends com.redshape.io.server.ErrorCodes {

	protected ErrorCodes( String message ) {
		super(message);
	}
	
	public ErrorCodes( ErrorCodes code, Throwable e ) {
		super(code, e);
	}
	
	public static ErrorCodes EXCEPTION_INVALID_API_KEY = new ErrorCodes("Error.InvalidApiKey");
	
	public static ErrorCodes EXCEPTION_AUTHENTICATION_FAIL = new ErrorCodes("Error.AuthenticationFail");
	
	public static ErrorCodes EXCEPTION_INACTIVE_API_KEY = new ErrorCodes("Error.InactiveApiKey");
	
	public static ErrorCodes EXCEPTION_EXPIRED_API_KEY = new ErrorCodes("Error.ExpiredApiKey");
}
