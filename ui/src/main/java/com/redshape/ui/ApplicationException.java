package com.redshape.ui;

/**
 * @author nikelin
 */
public class ApplicationException extends Exception {

	public ApplicationException() {
		super();
	}
	
	public ApplicationException( String message ) {
		super(message);
	}
	
	public ApplicationException( String message, Throwable e ) {
		super(message, e);
	}
	
}
