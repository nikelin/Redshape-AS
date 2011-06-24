package com.redshape.daemon.jobs.types;

import com.redshape.daemon.jobs.IJob;

import java.util.Date;

public interface IScheduleJob extends IJob {
	
	public Date getDatePoint();
	
}
