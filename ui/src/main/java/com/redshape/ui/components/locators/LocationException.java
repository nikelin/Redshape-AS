package com.redshape.ui.components.locators;

public class LocationException extends Exception {
	private static final long serialVersionUID = -664191046159035395L;

	public LocationException() {
		super();
	}
	
	public LocationException( String message ) {
		super(message);
	}
	
	public LocationException( String message, Throwable e ) {
		super(message, e);
	}
	
}
