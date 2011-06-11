package com.redshape.persistence.dao;

/**
 * @author nikelin
 */
public class ManagerException extends Exception  {
	private static final long serialVersionUID = 6738337053586756325L;

	public ManagerException( String message ) {
        super(message);
    }
    
    public ManagerException( String message, Throwable e ) {
    	super( message, e );
    }

    public ManagerException() {
        super();
    }
}