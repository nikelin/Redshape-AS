package com.redshape.plugins.loaders;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:56:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginLoaderException extends Exception {
	private static final long serialVersionUID = 2695731928486258626L;

	public PluginLoaderException() {}

    public PluginLoaderException( String message ) {
        super(message);
    }

}
