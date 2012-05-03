package com.redshape.jobs;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractJob implements IJob {
	private static final long serialVersionUID = -5210141269965829207L;
	
	private Date created;
	private UUID id;
	private JobStatus state;

	public AbstractJob() {
		this( UUID.randomUUID() );
	}

	public AbstractJob( UUID jobId ) {
		this.created = new Date();

		if ( jobId != null ) {
			this.setJobId(jobId);
		}
	}

	@Override
	public void setState(JobStatus status) {
		this.state = status;
	}

	@Override
	public JobStatus getState() {
		return this.state;
	}

	@Override
	public Date getCreated() {
		return this.created;
	}
	
	protected UUID generateUUID() {
		return UUID.randomUUID();
	}
	
	@Override
	public void setJobId(UUID id) {
		this.id = id;
	}
	
	@Override
	public UUID getJobId() {
		return this.id;
	}
	
}
