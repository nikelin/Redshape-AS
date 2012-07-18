package com.redshape.jobs.sources;

import com.redshape.jobs.IJobsDAO;
import com.redshape.jobs.IPersistenceJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.JobStatus;
import com.redshape.jobs.result.IJobResult;
import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.DAOFacade;
import com.redshape.persistence.utils.ISessionManager;
import com.redshape.utils.Commons;
import com.redshape.utils.events.AbstractEventDispatcher;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.redshape.daemon.jobs.sources
 * @user cyril
 * @date 6/21/11 5:52 PM
 */
public class DAOJobSource extends AbstractEventDispatcher
        implements IJobSource<IPersistenceJob> {
    private static final Logger log = Logger.getLogger(IJobSource.class);

    private String name;
    private DAOFacade facade;
    private ISessionManager sessionManager;
    private List<? extends IJobsDAO<?>> sources = new ArrayList<IJobsDAO<?>>();
    private int chunkSize;
    private int updateInterval;

    public DAOJobSource( String name,
                         ISessionManager sessionManager,
                         List<? extends IJobsDAO<?>> sources,
                         int workChunkSize,
                         int updateInterval ) {
        super();

        Commons.checkNotNull(name);
        Commons.checkNotNull(sessionManager);
        Commons.checkNotNull(sources);

        this.name = name;
        this.updateInterval = updateInterval;
        this.chunkSize = workChunkSize;
        this.sessionManager = sessionManager;
        this.sources = sources;
    }

    public String getName() {
        return name;
    }

    protected ISessionManager getSessionManager() {
        return this.sessionManager;
    }

    protected int getChunkSize() {
        return chunkSize;
    }

    @Override
    public int getUpdateInterval() {
        return this.updateInterval;
    }

    @Override
    public void complete(IPersistenceJob job, IJobResult result ) throws JobException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPersistenceJob save( IPersistenceJob entity) throws JobException {
        try {
			this.getSessionManager().open();
            return this.facade.<IPersistenceJob, IJobsDAO<IPersistenceJob>>getDAO(entity.getClass())
                    .save(entity);
        } catch ( DAOException e ) {
            throw new JobException( e.getMessage(), e );
        }
    }

    @Override
    public List<IPersistenceJob> fetch() throws JobException {
		try {
            List<IPersistenceJob> jobs = new ArrayList<IPersistenceJob>();
            for ( IJobsDAO<?> source : this.sources ) {
                this.getSessionManager().open();
                List<? extends IPersistenceJob> result = source
                                .findByStatus( JobStatus.WAITING )
                                    .offset(0)
                                    .limit(this.chunkSize)
                                        .list();
                for ( IPersistenceJob job : result ) {
                    job.setState( JobStatus.PROCESSING );
                    jobs.add(this.save(job));
                }
            }

            return jobs;
        } catch ( Throwable e ) {
            throw new JobException( e.getMessage(), e );
        }
    }

    @Override
    public void asyncRun(IPersistenceJob job) throws JobException {
        throw new UnsupportedOperationException("Not supported");
    }
}
