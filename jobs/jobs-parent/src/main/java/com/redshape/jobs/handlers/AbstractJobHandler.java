package com.redshape.jobs.handlers;

import com.redshape.jobs.IJob;
import com.redshape.jobs.result.IJobResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.UUID;

public abstract class AbstractJobHandler<T extends IJob, V extends IJobResult>
                                                implements IJobHandler<T, V>,
                                                           ApplicationContextAware {
    private ApplicationContext context;

    protected ApplicationContext getContext() {
        return this.context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    abstract protected V createJobResult( UUID jobId );
	
}
