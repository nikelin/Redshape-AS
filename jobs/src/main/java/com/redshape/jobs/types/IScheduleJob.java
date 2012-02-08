package com.redshape.jobs.types;

import com.redshape.jobs.IJob;

import java.util.Date;

public interface IScheduleJob extends IJob {
	
	public Date getDatePoint();
	
}
