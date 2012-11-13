/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.jobs.jpa.sources;

import com.redshape.jobs.IJobsDAO;
import com.redshape.jobs.IPersistenceJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.JobStatus;
import com.redshape.jobs.result.IJobResult;
import com.redshape.jobs.sources.IJobSource;
import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.DAOFacade;
import com.redshape.persistence.utils.ISessionManager;
import com.redshape.utils.Commons;
import com.redshape.utils.events.AbstractEventDispatcher;
import org.apache.log4j.Logger;

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

    private DAOFacade daoFacade;
    private ISessionManager sessionManager;
    private List<? extends IJobsDAO<?>> sources = new ArrayList<IJobsDAO<?>>();

    private int chunkSize;
    private int updateInterval;
    private int resultAwaitDelay;

    public DAOJobSource( DAOFacade daoFacade,
                         String name,
                         ISessionManager sessionManager,
                         List<? extends IJobsDAO<?>> sources,
                         int workChunkSize,
                         int resultAwaitDelay,
                         int updateInterval ) {
        super();

        Commons.checkNotNull(name);
        Commons.checkNotNull(sessionManager);
        Commons.checkNotNull(sources);

        this.daoFacade = daoFacade;

        this.name = name;
        this.updateInterval = updateInterval;
        this.resultAwaitDelay = resultAwaitDelay;
        this.chunkSize = workChunkSize;
        this.sessionManager = sessionManager;
        this.sources = sources;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getResultAwaitDelay() {
        return this.resultAwaitDelay;
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
			this.sessionManager.open();
            return this.daoFacade.<IPersistenceJob, IJobsDAO<IPersistenceJob>>getDAO(entity.getClass())
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
                this.sessionManager.open();
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
