package com.redshape.plugins.packagers;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.packagers
 * @date 10/11/11 1:41 PM
 */
public class PackagerException extends Exception {

	public PackagerException() {
		this( (String) null );
	}

	public PackagerException(String message) {
		this(message, null);
	}

	public PackagerException(String message, Throwable cause) {
		super(message, cause);
	}
}
