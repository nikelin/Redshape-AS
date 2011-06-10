package com.redshape.struct;

/**
 * Exception which may throws by nested set managers, etc.
 *
 * @author nikelin
 */
public class TreeNodeException extends Exception {
	private static final long serialVersionUID = 6464373233377951827L;

	public TreeNodeException() {
		super();
	}
	
	public TreeNodeException( String message ) {
		super( message );
	}
	
	public TreeNodeException( String message, Throwable e ) {
		super( message, e );
	}
	
}
