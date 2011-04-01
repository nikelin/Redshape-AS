package com.redshape.api;

public class ErrorCodes extends com.redshape.io.server.ErrorCodes {

	protected ErrorCodes( String message ) {
		super(message);
	}
	
	public ErrorCodes( ErrorCodes code, Throwable e ) {
		super(code, e);
	}
	
	public static final ErrorCodes EXCEPTION_REQUEST_METHOD_NOT_EXISTS = new ErrorCodes("Error.RequestedMethodNotExists");
	
	public static final ErrorCodes EXCEPTION_INVALID_API_KEY = new ErrorCodes("Error.InvalidApiKey");
	
	public static final ErrorCodes EXCEPTION_AUTHENTICATION_FAIL = new ErrorCodes("Error.AuthenticationFail");
	
	public static final ErrorCodes EXCEPTION_INACTIVE_API_KEY = new ErrorCodes("Error.InactiveApiKey");
	
	public static final ErrorCodes EXCEPTION_EXPIRED_API_KEY = new ErrorCodes("Error.ExpiredApiKey");
	
	public static final ErrorCodes EXCEPTION_WRONG_REQUEST_BODY = new ErrorCodes("Error.WrongRequestBody");
	
	public static final ErrorCodes EXCEPTION_ACCESS_DENIED = new ErrorCodes("Error.AccessDenied");
}
