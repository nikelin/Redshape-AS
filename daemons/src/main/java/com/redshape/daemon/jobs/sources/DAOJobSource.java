package com.redshape.daemon.jobs.sources;

import com.redshape.daemon.jobs.IJobsDAO;
import com.redshape.daemon.jobs.IPersistenceJob;
import com.redshape.daemon.jobs.JobException;
import com.redshape.daemon.jobs.JobStatus;
import com.redshape.persistence.dao.DAOException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @package com.redshape.daemon.jobs.sources
 * @user cyril
 * @date 6/21/11 5:52 PM
 */
public class DAOJobSource implements IJobSource<IPersistenceJob> {
    private static final Logger log = Logger.getLogger(IJobSource.class);

    private IJobsDAO<?> source;
    private int chunkSize = 30;
    private int period;

    public DAOJobSource( IJobsDAO<?> source ) {
        this.source = source;
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
            this.getSource().save( entity );
        } catch ( DAOException e ) {
            throw new JobException( e.getMessage(), e );
        }
    }

    @Override
    public synchronized List<IPersistenceJob> fetch() throws JobException {
        try {
            List<IPersistenceJob> jobs = this.getSource().findByStatus( JobStatus.WAITING, 0, this.chunkSize );
            for ( IPersistenceJob job : jobs ) {
                job.setState( JobStatus.PROCESSING );

                job = this.getSource().save( job );
            }

            return jobs;
        } catch ( DAOException e ) {
            log.error( e.getMessage(), e );
            throw new JobException();
        }
    }
}
