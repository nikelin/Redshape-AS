package com.redshape.io.protocols.core.writers;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers
 * @date Apr 1, 2010
 */
public class WriterException extends Exception {
	
	public WriterException() {
		super();
	}
	
	public WriterException( String message ) {
		super(message);
	}
	
	public WriterException( String message, Throwable exception ) {
		super(message, exception);
	}
	
}
