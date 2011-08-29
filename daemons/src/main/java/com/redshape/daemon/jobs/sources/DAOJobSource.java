package com.redshape.daemon.jobs.sources;

import com.redshape.daemon.jobs.IJobsDAO;
import com.redshape.daemon.jobs.IPersistenceJob;
import com.redshape.daemon.jobs.JobException;
import com.redshape.daemon.jobs.JobStatus;
import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.utils.EntityManagerUtils;
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
public class DAOJobSource implements IJobSource<IPersistenceJob>, ApplicationContextAware {
    private static final Logger log = Logger.getLogger(IJobSource.class);

	private ApplicationContext context;
    private IJobsDAO<?> source;
    private int chunkSize = 30;
    private int period;

    public DAOJobSource( IJobsDAO<?> source ) {
        this.source = source;
    }

	public ApplicationContext getContext() {
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
			EntityManagerUtils.openEntityManager( this.getContext() );
            this.getSource().save( entity );
        } catch ( DAOException e ) {
            throw new JobException( e.getMessage(), e );
        } finally {
			EntityManagerUtils.closeEntityManager(context);
		}
    }

    @Override
    public List<IPersistenceJob> fetch() throws JobException {
		try {
			EntityManagerUtils.openEntityManager( this.getContext() );
            List<IPersistenceJob> jobs = this.getSource().findByStatus( JobStatus.WAITING, 0, this.chunkSize );
            for ( IPersistenceJob job : jobs ) {
                job.setState( JobStatus.PROCESSING );

                job = this.getSource().save( job );
            }

            return jobs;
        } catch ( Throwable e ) {
			EntityManagerUtils.closeEntityManager( this.getContext() );
            log.error( e.getMessage(), e );
            throw new JobException( e.getMessage(), e );
        }
    }
}
