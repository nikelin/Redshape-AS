package com.redshape.auth;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 4, 2010
 * Time: 2:13:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 2360236975649688241L;

	public AuthenticationException() {
		this(null);
	}
	
	public AuthenticationException(String message) {
        this(message, null);
    }

    public AuthenticationException( String message, Throwable e ) {
        super(message, e);
    }

}
