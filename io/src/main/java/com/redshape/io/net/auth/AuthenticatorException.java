package com.redshape.io.net.auth;

import com.redshape.io.NetworkInteractionException;


/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatorException extends NetworkInteractionException {

    public AuthenticatorException() {
        this(null);
    }

    public AuthenticatorException( String message ) {
        this(message, null);
    }

	public AuthenticatorException(String message, Throwable e) {
		super(message, e);
	}
}
