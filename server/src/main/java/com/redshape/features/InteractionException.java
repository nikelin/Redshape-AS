package com.redshape.features;

import com.redshape.io.server.ErrorCodes;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 6, 2010
 * Time: 12:30:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class InteractionException extends Exception {

    public InteractionException() {
        this(ErrorCodes.EXCEPTION_INTERNAL);
    }

    public InteractionException( ErrorCodes e ) {
    	this( e.getMessage(), e );
    }
    
    public InteractionException( String message, Throwable cause ) {
        super( message, cause );
    }

    public InteractionException( String code ) {
        super(code);
    }

}
