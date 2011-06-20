package com.redshape.daemon.jobs.handlers;

import com.redshape.daemon.jobs.IJob;
import com.redshape.daemon.jobs.result.IJobResult;

public interface IJobHandler<T extends IJob, V extends IJobResult> {

	public V handle(T job) throws HandlingException;
	
	public void cancel() throws HandlingException;
	
}
