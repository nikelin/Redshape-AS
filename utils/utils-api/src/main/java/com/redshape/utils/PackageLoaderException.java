package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 22, 2010
 * Time: 1:16:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class PackageLoaderException extends Exception {
	private static final long serialVersionUID = 5900125090232239656L;

	public PackageLoaderException() {
	}

	public PackageLoaderException(String s) {
		super(s);
	}

	public PackageLoaderException(String s, Throwable throwable) {
		super(s, throwable);
	}

}
