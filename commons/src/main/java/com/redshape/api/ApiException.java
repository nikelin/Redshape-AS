package com.redshape.api;

public class ApiException extends Exception {
	private static final long serialVersionUID = 9137295792552366089L;

	public ApiException() {
        super();
    }

    public ApiException( String message ) {
        super(message);
    }
    
    public ApiException( String message, Throwable e ) {
    	super( message, e );
    }

}
