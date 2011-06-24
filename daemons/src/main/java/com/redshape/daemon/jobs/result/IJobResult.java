package com.redshape.daemon.jobs.result;

import com.redshape.daemon.jobs.JobStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public interface IJobResult extends Serializable {
	
	public UUID getJobId();
	
	public boolean isFinished();
	
	public Integer getProgress();

	public void setCompletionDate(Date date);

	public Date getCompletionDate();

	public JobStatus getStatus();
}
