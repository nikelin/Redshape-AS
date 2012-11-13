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

package com.redshape.jobs.events;

import com.redshape.jobs.IJob;
import com.redshape.utils.events.AbstractEvent;

public class JobEvent extends AbstractEvent {
	private static final long serialVersionUID = 8959450913190231009L;
	
	private IJob job;
	private Integer daemonId;
	
	public JobEvent( IJob job ) {
		this.job = job;
	}
	
	@SuppressWarnings("unchecked")
	public <V extends IJob> V getJob() {
		return (V) this.job;
	}
	
}
