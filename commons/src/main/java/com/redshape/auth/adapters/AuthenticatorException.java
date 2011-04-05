package com.redshape.auth.adapters;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 4, 2010
 * Time: 2:13:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatorException extends Exception {
	private static final long serialVersionUID = 2360236975649688241L;

	public AuthenticatorException() {
		this(null);
	}
	
	public AuthenticatorException( String message ) {
        super(message);
    }

}
