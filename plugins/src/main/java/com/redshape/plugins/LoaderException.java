package com.redshape.plugins;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.loaders
 * @date 10/11/11 1:27 PM
 */
public class LoaderException extends Exception {

	public LoaderException() {
		this(null);
	}

	public LoaderException( String message ) {
		this(message, null);
	}

	public LoaderException( String message, Throwable e ) {
		super(message, e);
	}

}
