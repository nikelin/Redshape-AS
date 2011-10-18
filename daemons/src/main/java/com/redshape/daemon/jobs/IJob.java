package com.redshape.daemon.jobs;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public interface IJob extends Serializable {
	
	public UUID getJobId();
	
	public void setJobId(UUID id);

    public void setState( JobStatus status );

    public JobStatus getState();

	public void setUpdated( Date date );

	public Date getUpdated();

	public Date getCreated();
	
}
