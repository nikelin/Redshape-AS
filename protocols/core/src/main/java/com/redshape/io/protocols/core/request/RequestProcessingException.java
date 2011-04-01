package com.redshape.io.protocols.core.request;

import com.redshape.io.server.ErrorCodes;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestProcessingException extends RequestException {
	private static final long serialVersionUID = -2034936193159056934L;

	public RequestProcessingException() {
		super();
	}
	
	public RequestProcessingException( ErrorCodes code ) {
		super(code);
	}
	
	public RequestProcessingException( String message ) {
		super(message);
	}
	
	public RequestProcessingException( String message, Throwable e ) {
		super(message, e);
	}

}
