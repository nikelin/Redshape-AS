package com.redshape.daemon.jobs;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public interface IJob extends Serializable {
	
	public UUID getJobId();
	
	public void setJobId(UUID id);
	
	public Date getCreated();
	
}
