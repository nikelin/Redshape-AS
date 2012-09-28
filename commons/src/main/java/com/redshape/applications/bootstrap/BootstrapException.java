package com.redshape.applications.bootstrap;

import com.redshape.applications.ApplicationException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 1:58:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class BootstrapException extends ApplicationException {
	private static final long serialVersionUID = 1735342636881160038L;

	public BootstrapException() {
    	super();
    }

    public BootstrapException( String message ) {
        super(message);
    }
    
    public BootstrapException( String message, Throwable e ) {
    	super( message, e );
    }

}
