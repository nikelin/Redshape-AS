package com.redshape.plugins;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:45:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginInitException extends Exception {
	private static final long serialVersionUID = -8988237725489764268L;
	
	public PluginInitException() {
		this(null);
	}
	
	public PluginInitException( String message ) {
		this(message, null);
	}
	
	public PluginInitException( String message, Throwable e ) {
		super(message, e);
	}
}
