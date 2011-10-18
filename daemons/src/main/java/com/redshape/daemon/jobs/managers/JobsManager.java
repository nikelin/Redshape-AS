package com.redshape.daemon.jobs.managers;

import com.redshape.daemon.jobs.IJob;
import com.redshape.daemon.jobs.handlers.AbstractAwareJobHandler;
import com.redshape.daemon.jobs.handlers.HandlingException;
import com.redshape.daemon.jobs.handlers.IJobHandler;
import com.redshape.daemon.jobs.result.IJobResult;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JobsManager implements IJobsManager {
	private Map<Class<? extends IJob>, IJobHandler<?, ?>> handlers =
							new HashMap<Class<? extends IJob>, IJobHandler<?, ?>>();
	private ApplicationContext context;
	private ExecutorService threadsExecutor;
	
	private Map<UUID, HandlingDescriptor<?,?>> jobs = new HashMap<UUID, HandlingDescriptor<?,?>>();
	
	public JobsManager() {
		this.threadsExecutor = Executors.newFixedThreadPool(100);
	}
	
	protected ExecutorService getThreadsExecutor() {
		return this.threadsExecutor;
	}

    @Override
	public void setApplicationContext( ApplicationContext context ) {
		this.context = context;
	}
	
	public ApplicationContext getContext() {
		return this.context;
	}
	
	public void setHandlers( Map<Class<? extends IJob>, IJobHandler<?,?>> handlers ) {
		this.handlers = handlers;
	}
	
	public Map<Class<? extends IJob>, IJobHandler<?, ?>> getHandlers() {
		return this.handlers;
	}
	
	@Override
	public void cancel( UUID id ) throws HandlingException {
		this.jobs.get(id).cancel();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IJob, R extends IJobResult> Future<R> execute( T job ) throws HandlingException {
		IJobHandler<T,R> handler = (IJobHandler<T,R>) this.handlers.get( job.getClass() );
		if ( handler instanceof AbstractAwareJobHandler) {
			( (AbstractAwareJobHandler<T, R>) handler ).setContext( this.getContext() );
		}
		
		if ( handler == null ) {
			throw new HandlingException("Handler for given job " + job.getClass().getCanonicalName() + " does not exists");
		}

		return this.getThreadsExecutor().submit( new HandlingDescriptor<T, R>( handler, job ) );
	}
	
	public class HandlingDescriptor<T extends IJob, R extends IJobResult> implements Callable<R> {
		private IJobHandler<T, R> handler;
		private T job;
		
		public HandlingDescriptor( IJobHandler<T, R> handler, T job ) {
			this.job = job;
			this.handler = handler;
		}

		@Override
		public R call() throws Exception {
			return this.handler.handle(this.job);
		}
		
		public void cancel() throws HandlingException {
			this.handler.cancel();
		}

	}
	
}
