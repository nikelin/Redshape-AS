package com.redshape.jobs.result;

import com.redshape.jobs.JobStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface IJobResult extends Serializable {

	public <T> Map<JobResultAttribute, T> getAttributes();

	public <T> T getAttribute( JobResultAttribute attribute );

	public void setAttribute( JobResultAttribute attribute, Object value );

	public UUID getJobId();
	
	public boolean isFinished();
	
	public Integer getProgress();

	public void setCompletionDate(Date date);

	public Date getCompletionDate();

	public JobStatus getStatus();
}
