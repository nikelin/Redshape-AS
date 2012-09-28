package com.redshape.commands;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:29:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecutionException extends Exception {
	private static final long serialVersionUID = -6865452739736849516L;

	public ExecutionException() {
		this(null);
	}

    public ExecutionException( String message ) {
        super(message);
    }

}
