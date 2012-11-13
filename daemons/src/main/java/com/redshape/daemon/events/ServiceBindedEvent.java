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
import com.redshape.daemon.IDaemonEvent;
import com.redshape.daemon.IRemoteService;

public class ServiceBindedEvent extends AbstractEvent implements IDaemonEvent {
	private static final long serialVersionUID = 8991847611607711518L;
	
	private IRemoteService service;
	private Integer daemonId;
	
	public ServiceBindedEvent( IRemoteService service ) {
		this.service = service;
	}
	
	public <T extends IRemoteService> T getService() {
		return (T) this.service;
	}

	public void setDaemonId( Integer id ) {
		this.daemonId = id;
	}

	public Integer getDaemonId() {
		return this.daemonId;
	}
	
}
