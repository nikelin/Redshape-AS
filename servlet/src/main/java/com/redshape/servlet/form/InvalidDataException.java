package com.redshape.servlet.form;

public class InvalidDataException extends Exception {
	private static final long serialVersionUID = -9060426412136716714L;

	public InvalidDataException() {
		this(null);
	}
	
	public InvalidDataException( String message ) {
		this(message, null);
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
