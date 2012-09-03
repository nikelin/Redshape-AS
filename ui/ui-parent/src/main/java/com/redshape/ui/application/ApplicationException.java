package com.redshape.ui.application;

/**
 * @author nikelin
 */
public class ApplicationException extends Exception {
	private static final long serialVersionUID = -8970117526669120443L;

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
