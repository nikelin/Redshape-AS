package com.redshape.daemon.jobs.managers;

import com.redshape.daemon.jobs.IJob;
import com.redshape.daemon.jobs.handlers.HandlingException;
import com.redshape.daemon.jobs.result.IJobResult;
import org.springframework.context.ApplicationContext;

import java.util.UUID;
import java.util.concurrent.Future;

public interface IJobsManager {
	
	// TODO remove from interface
	public void setContext(ApplicationContext context);
	
	public <T extends IJob, R extends IJobResult> Future<R> execute(T job)
				throws HandlingException;
	
	public void cancel(UUID jobId) throws HandlingException;
	
}
