package com.redshape.io.server;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 18, 2010
 * Time: 12:34:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerException extends Exception {
	private static final long serialVersionUID = -9135005376527904008L;

	public ServerException() {
    	super();
    }
    
    public ServerException( ErrorCodes code ) {
    	this( code.getMessage(), code );
    }
    
    public ServerException( String message ) {
    	super(message);
    }

    public ServerException( String message, Throwable e ) {
        super( message, e );
    }
    
}
