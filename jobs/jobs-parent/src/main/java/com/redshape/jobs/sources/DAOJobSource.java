package com.redshape.jobs.sources;

import com.redshape.jobs.IJobsDAO;
import com.redshape.jobs.IPersistenceJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.JobStatus;
import com.redshape.jobs.result.IJobResult;
import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.utils.ISessionManager;
import com.redshape.utils.events.AbstractEventDispatcher;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * @package com.redshape.daemon.jobs.sources
 * @user cyril
 * @date 6/21/11 5:52 PM
 */
public class DAOJobSource extends AbstractEventDispatcher
        implements IJobSource<IPersistenceJob>, ApplicationContextAware {
    private static final Logger log = Logger.getLogger(IJobSource.class);

    private String name;
	private ApplicationContext context;
    private ISessionManager sessionManager;
    private IJobsDAO<?> source;
    private int chunkSize;
    private int updateInterval;

    public DAOJobSource( String name,
                         ISessionManager sessionManager,
                         IJobsDAO<?> source,
                         int workChunkSize,
                         int updateInterval ) {
        super();

        this.name = name;
        this.updateInterval = updateInterval;
        this.chunkSize = workChunkSize;
        this.sessionManager = sessionManager;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    protected ISessionManager getSessionManager() {
        return this.sessionManager;
    }

	protected ApplicationContext getContext() {
		return context;
	}

    protected IJobsDAO<IPersistenceJob> getSource() {
        return (IJobsDAO<IPersistenceJob>) source;
    }

    protected int getChunkSize() {
        return chunkSize;
    }

    @Override
    public int getUpdateInterval() {
        return this.updateInterval;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void complete(IPersistenceJob job, IJobResult result ) throws JobException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save( IPersistenceJob entity) throws JobException {
        try {
			this.getSessionManager().open();
            this.getSource().save(entity);
        } catch ( DAOException e ) {
            throw new JobException( e.getMessage(), e );
        } finally {
            try {
			    this.getSessionManager().close();
            } catch ( DAOException e ) {
                log.error( e.getMessage(), e );
            }
		}
    }

    @Override
    public List<IPersistenceJob> fetch() throws JobException {
		try {
			this.getSessionManager().open();
            List<IPersistenceJob> jobs = this.getSource()
                            .findByStatus( JobStatus.WAITING )
                                .offset(0)
                                .limit(this.chunkSize)
                                    .list();
            for ( IPersistenceJob job : jobs ) {
                job.setState( JobStatus.PROCESSING );

                job = this.getSource().save( job );
            }

            return jobs;
        } catch ( Throwable e ) {
            throw new JobException( e.getMessage(), e );
        } finally {
            try {
                this.getSessionManager().close();
            } catch ( DAOException e ) {
                log.error( e.getMessage(), e );
            }
        }
    }

    @Override
    public void asyncRun(IPersistenceJob job) throws JobException {
        throw new UnsupportedOperationException("Not supported");
    }
}
