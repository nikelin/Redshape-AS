package com.redshape.daemon.jobs;

public interface ITimerJob extends IJob {
	
	public Integer getTicks();
	
	public Boolean isUnlimited();
	
	public Integer getInterval();
	
	public Integer getWait();
	
}
