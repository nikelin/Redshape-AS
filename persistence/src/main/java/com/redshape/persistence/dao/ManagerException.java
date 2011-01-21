package com.redshape.persistence.dao;

/**
 * @author nikelin
 */
public class ManagerException extends Exception  {

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