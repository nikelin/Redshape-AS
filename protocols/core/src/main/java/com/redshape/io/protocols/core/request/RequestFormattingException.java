package com.redshape.io.protocols.core.request;


/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestFormattingException extends RequestException {
	
	public RequestFormattingException() {
		super();
	}
	
	public RequestFormattingException( String message ) {
		super( message );
	}
	
	public RequestFormattingException( String message, Throwable e ) {
		super( message, e );
	}
	
}
