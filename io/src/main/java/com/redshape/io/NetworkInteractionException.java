package com.redshape.io;


import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkInteractionException extends IOException {
	private static final long serialVersionUID = 9148204335399558919L;

	public NetworkInteractionException() {
        this(null);
    }

    public NetworkInteractionException( String message ) {
        this( message, null );
    }

	public NetworkInteractionException( String message, Throwable e ) {
		super(message, e);
	}

}
