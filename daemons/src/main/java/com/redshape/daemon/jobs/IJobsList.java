package com.redshape.daemon.jobs;

import java.util.List;

public interface IJobsList<T> extends IJob, Iterable<T> {
	
	public List<T> getJobs();
	
	public Integer size();
	
}
