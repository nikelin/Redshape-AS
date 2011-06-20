package com.redshape.daemon.jobs.handlers;

import com.redshape.daemon.jobs.IJob;
import com.redshape.daemon.jobs.result.IJobResult;

import java.util.UUID;

public abstract class AbstractJobHandler<T extends IJob, V extends IJobResult> implements IJobHandler<T, V> {

	abstract protected V createJobResult( UUID jobId );
	
}
