package com.redshape.ui.views;

/**
 * View-related exception
 *
 * @author nikelin
 */
public class ViewException extends Exception {
	private static final long serialVersionUID = -7641085451234058629L;

	public ViewException() {
		this(null);
	}
	
	public ViewException( String message ) {
		this(message, null);
	}
	
	public ViewException( String message, Throwable exception ) {
		super(message, exception );
	}
	
}
