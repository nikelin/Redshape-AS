package com.redshape.utils.beans.bindings.accessors;

import com.redshape.utils.beans.bindings.BindingException;

public class AccessException extends BindingException {
	private static final long serialVersionUID = 6629360629264688640L;

	public AccessException() {
		this(null);
	}
	
	public AccessException( String message ) {
		this(message, null);
	}
	
	public AccessException( String message, Throwable e ) {
		super(message, e);
	}
	
}
