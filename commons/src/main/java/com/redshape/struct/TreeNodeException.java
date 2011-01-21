package com.redshape.struct;

/**
 * Exception which may throws by nested set managers, etc.
 *
 * @author nikelin
 */
public class TreeNodeException extends Exception {
	
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
