package com.redshape.daemon.jobs.events;

import com.redshape.daemon.IDaemonEvent;
import com.redshape.daemon.jobs.IJob;

public interface IJobEvent extends IDaemonEvent {
	
	public <V extends IJob> V getJob();
	
}
