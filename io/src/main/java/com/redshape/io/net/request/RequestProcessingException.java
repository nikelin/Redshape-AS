package com.redshape.io.net.request;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestProcessingException extends RequestException {
	
	public RequestProcessingException() {
		super();
	}
	
	public RequestProcessingException( String message ) {
		super(message);
	}
	
	public RequestProcessingException( String message, Throwable e ) {
		super(message, e);
	}

}
