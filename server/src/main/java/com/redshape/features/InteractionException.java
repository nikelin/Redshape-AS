package com.redshape.features;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 6, 2010
 * Time: 12:30:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class InteractionException extends Exception {

    public InteractionException() {
        super("ErrorCode.EXCEPTION_INTERNAL");
    }

    public InteractionException( String message, Exception cause ) {
        super( message, cause );
    }

    public InteractionException( String code ) {
        super(code);
    }

}
