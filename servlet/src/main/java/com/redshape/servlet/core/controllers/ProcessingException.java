package com.redshape.servlet.core.controllers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessingException extends Exception {
	private static final long serialVersionUID = -4676201952592409938L;

	public ProcessingException() {
        this(null);
    }

    public ProcessingException( String message ) {
        this( message, null );
    }
    
    public ProcessingException( String message, Throwable e ) {
    	super(message, e);
    }

}
