package com.redshape.utils.config;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 10, 2010
 * Time: 12:49:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigException extends Exception {

    public ConfigException() {
        this(null);
    }

    public ConfigException( String message ) {
        this(message, null);
    }

	public ConfigException( String message, Throwable e ) {
		super(message, e);
	}

}
