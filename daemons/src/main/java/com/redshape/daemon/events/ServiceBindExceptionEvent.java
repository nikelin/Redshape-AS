package com.redshape.daemon.events;

import com.redshape.utils.events.AbstractEvent;

/**
 * @author nikelin
 * @date 13/04/11
 * @package com.redshape.daemon.events
 */
public class ServiceBindExceptionEvent extends AbstractEvent {
	private Exception exception;

	public ServiceBindExceptionEvent( Exception exception ) {
		super();

		this.exception = exception;
	}

	public Exception getException() {
		return this.exception;
	}

}
