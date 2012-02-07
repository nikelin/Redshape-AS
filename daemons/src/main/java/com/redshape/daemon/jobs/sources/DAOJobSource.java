package com.redshape.daemon.jobs.sources;

import com.redshape.daemon.jobs.IJobsDAO;
import com.redshape.daemon.jobs.IPersistenceJob;
import com.redshape.daemon.jobs.JobException;
import com.redshape.daemon.jobs.JobStatus;
import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.utils.ISessionManager;
import com.redshape.utils.events.AbstractEventDispatcher;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * @package com.redshape.daemon.jobs.sources
 * @user cyril
 * @date 6/21/11 5:52 PM
 */
public class DAOJobSource extends AbstractEventDispatcher
        implements IDAOJobSource<IPersistenceJob>, ApplicationContextAware {
    private static final Logger log = Logger.getLogger(IDAOJobSource.class);

	private ApplicationContext context;
    private ISessionManager sessionManager;
    private IJobsDAO<?> source;
    private int chunkSize = 30;
    private int period;

    public DAOJobSource( ISessionManager sessionManager, IJobsDAO<?> source ) {
        super();

        this.sessionManager = sessionManager;
        this.source = source;
    }

    protected ISessionManager getSessionManager() {
        return this.sessionManager;
    }

	protected ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    protected IJobsDAO<IPersistenceJob> getSource() {
        return (IJobsDAO<IPersistenceJob>) source;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
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
}
