package com.redshape.applications;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 20, 2010
 * Time: 1:54:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationException extends Exception {

    public ApplicationException() {
    	super();
    }

    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException( String message, Throwable e ) {
    	super( message, e );
    }

}