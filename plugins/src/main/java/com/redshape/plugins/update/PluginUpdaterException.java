package com.redshape.plugins.update;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:39:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginUpdaterException extends Exception {
	private static final long serialVersionUID = 6495281207924881936L;
	
	public PluginUpdaterException() {
		this(null);
	}
	
	public PluginUpdaterException( String message ) {
		this(message, null);
	}
	
	public PluginUpdaterException( String message, Throwable e ) {
		super(message, e);
	}
	
}
