package com.redshape.io.protocols.dispatchers;

import com.redshape.io.server.ServerException;

public class DispatcherException extends ServerException {
	
	public DispatcherException() {
		super();
	}
	
	public DispatcherException( String message ) {
		super(message);
	}
	
	public DispatcherException( String message, Throwable exception ) {
		super(message, exception);
	}
	
}
