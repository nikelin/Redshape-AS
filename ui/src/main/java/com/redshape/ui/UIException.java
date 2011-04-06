package com.redshape.ui;

public class UIException extends Exception {
	private static final long serialVersionUID = 9130572223305328055L;

	public UIException() {
		this(null);
	}
	
	public UIException( String message ) {
		this(message, null);
	}
	
	public UIException( String message, Throwable e ) {
		super(message, e);
	}
	
}
