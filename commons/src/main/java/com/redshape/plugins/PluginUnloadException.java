package com.redshape.plugins;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:19:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginUnloadException extends Exception {
	private static final long serialVersionUID = 3940171114658956363L;

	public PluginUnloadException() {
		this(null);
	}
	
	public PluginUnloadException( String message ) {
		this(message, null);
	}
	
	public PluginUnloadException( String message, Throwable e ) {
		super(message, e);
	}
	
}
