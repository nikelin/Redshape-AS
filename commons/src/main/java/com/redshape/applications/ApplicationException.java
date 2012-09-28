package com.redshape.applications;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 20, 2010
 * Time: 1:54:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationException extends Exception {
	private static final long serialVersionUID = -4311623864906387180L;

	public ApplicationException() {
    	this(null);
    }

    public ApplicationException(String message) {
        this(message, null);
    }
    
    public ApplicationException( String message, Throwable e ) {
    	super( message, e );
    }

}
