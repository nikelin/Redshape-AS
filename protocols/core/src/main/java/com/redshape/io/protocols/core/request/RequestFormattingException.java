package com.redshape.io.protocols.core.request;

import com.redshape.io.server.ErrorCodes;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestFormattingException extends RequestException {
	private static final long serialVersionUID = 6577863630653186742L;

	public RequestFormattingException() {
		super();
	}
	
	public RequestFormattingException( ErrorCodes code ) {
		super(code);
	}
	
	public RequestFormattingException( String message ) {
		super( message );
	}
	
	public RequestFormattingException( String message, Throwable e ) {
		super( message, e );
	}
	
}
