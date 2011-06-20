package com.redshape.daemon.jobs;

import java.util.Date;

public interface IScheduleJob extends IJob {
	
	public Date getDatePoint();
	
}
