package com.redshape.jobs.managers;

import com.redshape.jobs.IJob;
import com.redshape.jobs.handlers.AbstractAwareJobHandler;
import com.redshape.jobs.handlers.HandlingException;
import com.redshape.jobs.handlers.IJobHandler;
import com.redshape.jobs.result.IJobResult;
import com.redshape.jobs.sources.IJobSource;
import com.redshape.jobs.sources.SourceEvent;
import com.redshape.utils.events.IEventListener;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JobsManager implements IJobsManager {
    private static final Logger log = Logger.getLogger(JobsManager.class);

	private Map<Class<? extends IJob>, IJobHandler<?, ?>> handlers =
							new HashMap<Class<? extends IJob>, IJobHandler<?, ?>>();
	private ApplicationContext context;
	private ExecutorService threadsExecutor;
	private IJobSource source;
    
	private Map<UUID, HandlingDescriptor<?,?>> jobs = new HashMap<UUID, HandlingDescriptor<?,?>>();
	
    public JobsManager() {
        this(null);
    }
    
	public JobsManager( IJobSource source ) {
        this.source = source;
		this.threadsExecutor = Executors.newFixedThreadPool(100);

        if ( this.source != null ) {
            this.initSource();
        }
	}

    protected void initSource() {
        this.source.addEventListener(SourceEvent.class, new IEventListener<SourceEvent>() {
            @Override
            public void handleEvent(SourceEvent event) {
                try {
                    switch ( event.getType() ) {
                        case SCHEDULED:
                            JobsManager.this.execute( event.<IJob>getArg(0) );
                        break;
                        case CANCELLED:
                            JobsManager.this.cancel( event.<UUID>getArg(0) );
                        break;
                        default:
                            log.warn(String.format(
                                "Unknown jobs source event type: *%s*",
                                event.getType().name()
                            ));
                    }
                } catch ( Throwable e ) {
                    log.error( e.getMessage(), e );
                }
            }
        });
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

		return this.getThreadsExecutor().submit( new HandlingDescriptor<T, R>( this.source, handler, job ) );
	}

	public class HandlingDescriptor<T extends IJob, R extends IJobResult> implements Callable<R> {
        private IJobSource source;
		private IJobHandler<T, R> handler;
		private T job;
		
		public HandlingDescriptor( IJobSource source, IJobHandler<T, R> handler, T job ) {
            this.source = source;
			this.job = job;
			this.handler = handler;
		}

		@Override
		public R call() throws Exception {
            try {
                R result = this.handler.handle(this.job);
                if ( this.source != null ) {
                    this.source.complete( this.job, result );
                }
                
                return result;
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                throw new HandlingException( e.getMessage(), e);
            }
		}
		
		public void cancel() throws HandlingException {
			this.handler.cancel();
		}

	}
	
}
