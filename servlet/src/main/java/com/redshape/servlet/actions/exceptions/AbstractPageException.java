package com.redshape.servlet.actions.exceptions;

public abstract class AbstractPageException extends Exception {
	private static final long serialVersionUID = -5092263766328386258L;

	public AbstractPageException() {
		this(null);
	}
	
	public AbstractPageException( String message ) {
		this(message, null);
	}
	
	public AbstractPageException( String message, Throwable e ) {
		super(message, e);
	}
	
}
