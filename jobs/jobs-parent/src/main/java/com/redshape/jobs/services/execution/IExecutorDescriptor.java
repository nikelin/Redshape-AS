package com.redshape.jobs.services.execution;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.Map;

public interface IExecutorDescriptor extends Serializable {

	public URI getLocation();
	
	public Date getStartedOn();
	
	public <V> V getAttribute(String attribute);
	
	public <V> Map<String, V> getAttributes();

}
