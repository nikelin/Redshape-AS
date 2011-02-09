package com.redshape.api;

public class ApiException extends Exception {

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
