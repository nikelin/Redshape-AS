package com.redshape.io.protocols.core.request;

import com.redshape.io.server.ErrorCodes;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestException extends Exception {
	private static final long serialVersionUID = 4406027943392519052L;

	public RequestException() {
		super();
	}
	
	public RequestException( ErrorCodes code ) {
		this( code.getMessage(), code );
	}
	
	public RequestException( String message ) {
		super( message );
	}
	
	public RequestException( String message, Throwable e ) {
		super(message, e );
	}
}
