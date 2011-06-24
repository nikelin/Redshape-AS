package com.redshape.daemon.jobs.services.execution;

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
