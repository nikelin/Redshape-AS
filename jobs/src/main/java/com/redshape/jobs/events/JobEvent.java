package com.redshape.jobs.events;

import com.redshape.jobs.IJob;
import com.redshape.utils.events.AbstractEvent;

public class JobEvent extends AbstractEvent {
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
	
}
