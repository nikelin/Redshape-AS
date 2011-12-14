package com.redshape.daemon.events;

import com.redshape.utils.events.AbstractEvent;

/**
 * @author nikelin
 * @date 13/04/11
 * @package com.redshape.daemon.events
 */
public class ServiceBindExceptionEvent extends AbstractEvent {
	private Throwable exception;

	public ServiceBindExceptionEvent( Throwable exception ) {
		super();

		this.exception = exception;
	}

	public Throwable getException() {
		return this.exception;
	}

}
