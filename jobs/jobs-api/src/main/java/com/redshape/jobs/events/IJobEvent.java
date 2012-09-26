package com.redshape.jobs.events;

import com.redshape.jobs.IJob;
import com.redshape.utils.events.IEvent;

public interface IJobEvent extends IEvent {
	
	public <V extends IJob> V getJob();
	
}
