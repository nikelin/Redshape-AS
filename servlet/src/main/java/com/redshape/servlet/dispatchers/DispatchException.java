package com.redshape.servlet.dispatchers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchException extends Exception {
	private static final long serialVersionUID = 9148190945138130478L;

	public DispatchException() {
        this(null);
    }

    public DispatchException( String message ) {
        this( message, null );
    }
    
    public DispatchException( String message, Throwable e ) {
    	super(message, e);
    }

}
