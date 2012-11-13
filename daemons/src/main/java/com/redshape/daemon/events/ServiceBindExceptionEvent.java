/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
