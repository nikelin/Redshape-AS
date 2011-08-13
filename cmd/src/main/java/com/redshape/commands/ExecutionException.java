package com.redshape.commands;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:29:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecutionException extends Exception {

    public ExecutionException() {
        this(null);
    }

    public ExecutionException( String message ) {
        this(message, null);
    }

    public ExecutionException( String message, Throwable e ) {
        super( message, e );
    }

}
