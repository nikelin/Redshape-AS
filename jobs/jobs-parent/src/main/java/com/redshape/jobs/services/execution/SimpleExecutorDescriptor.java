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

package com.redshape.jobs.services.execution;

import java.net.URI;
import java.util.Date;
import java.util.Map;

public class SimpleExecutorDescriptor implements IExecutorDescriptor {
	private static final long serialVersionUID = 1741370630898839805L;

	private URI location;
	private Date startedOn;
	private Map<String, Object> attributes;
	
	public SimpleExecutorDescriptor( URI location, Date startedOn ) {
		this.location = location;
		this.startedOn = startedOn;
	}

	public void setLocation( URI location ) {
		this.location = location;
	}
	
	@Override
	public Date getStartedOn() {
		return this.startedOn;
	}
	
	@Override
	public URI getLocation() {
		return this.location;
	}

	@SuppressWarnings("unchecked")
	public <V> V getAttribute( String name ) {
		return (V) this.attributes.get(name);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V> Map<String, V> getAttributes() {
		return (Map<String, V>) this.attributes;
	}
	
}
