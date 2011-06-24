package com.redshape.daemon.jobs.types;

import com.redshape.daemon.jobs.IJob;

public interface ITimerJob extends IJob {
	
	public Integer getTicks();
	
	public Boolean isUnlimited();
	
	public Integer getInterval();
	
	public Integer getWait();
	
}
