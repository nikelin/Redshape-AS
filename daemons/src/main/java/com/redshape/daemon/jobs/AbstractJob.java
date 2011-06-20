package com.redshape.daemon.jobs;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractJob implements IJob {
	private static final long serialVersionUID = -5210141269965829207L;
	
	private Date created;
	private UUID id;
	
	public AbstractJob() {
		this.created = new Date();
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
