package com.redshape.utils;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.utils
 * @date Mar 16, 2010
 */
public class ObjectsLoaderException extends Exception {
	private static final long serialVersionUID = -4600134880409622984L;
	
	public ObjectsLoaderException() {
		this(null);
	}
	
	public ObjectsLoaderException( String message ) {
		this(message, null);
	}
	
	public ObjectsLoaderException( String message, Throwable cause ) {
		super(message, cause);
	}
	
}
