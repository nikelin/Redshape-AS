package com.redshape.jobs.types;

import com.redshape.jobs.IJob;

public interface ITimerJob extends IJob {
	
	public Integer getTicks();
	
	public Boolean isUnlimited();
	
	public Integer getInterval();
	
	public Integer getWait();
	
}
