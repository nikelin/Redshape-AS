package com.redshape.io.net.request;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestException extends Exception {

	public RequestException() {
		super();
	}
	
	public RequestException( String message ) {
		super( message );
	}
	
	public RequestException( String message, Throwable e ) {
		super(message, e );
	}
}
