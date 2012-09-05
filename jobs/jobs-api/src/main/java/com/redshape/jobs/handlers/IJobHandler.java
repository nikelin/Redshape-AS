package com.redshape.jobs.handlers;

import com.redshape.jobs.IJob;
import com.redshape.jobs.result.IJobResult;

public interface IJobHandler<T extends IJob, V extends IJobResult> {

	public V handle(T job) throws HandlingException;
	
	public void cancel() throws HandlingException;
	
}
