package com.redshape.daemon.jobs.events;

import com.redshape.utils.events.AbstractEvent;
import com.redshape.daemon.IDaemonEvent;
import com.redshape.daemon.jobs.IJob;

public class JobEvent extends AbstractEvent implements IDaemonEvent {
	private static final long serialVersionUID = 8959450913190231009L;
	
	private IJob job;
	private Integer daemonId;
	
	public JobEvent( IJob job ) {
		this.job = job;
	}
	
	@SuppressWarnings("unchecked")
	public <V extends IJob> V getJob() {
		return (V) this.job;
	}

	@Override
	public Integer getDaemonId() {
		return this.daemonId;
	}

	@Override
	public void setDaemonId(Integer id) {
		this.daemonId = id;
	}
	
}
