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

package com.redshape.jobs;

import com.redshape.persistence.entities.AbstractDTO;
import com.redshape.utils.Commons;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 4/27/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPersistenceJobDTO extends AbstractDTO implements IPersistenceJob {
    private Long id;
    private UUID jobId;
    private JobStatus state;
    private Integer failuresCount;
    private Date created;
    private Date updated;
    private Date processedDate;

    protected AbstractPersistenceJobDTO() {
        super();
    }

    protected AbstractPersistenceJobDTO( Class<? extends IPersistenceJob> clazz ) {
        super(clazz);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId( Long id ) {
        this.id = id;
    }

    @Override
    public JobStatus getState() {
        return state;
    }

    @Override
    public void setState(JobStatus state) {
        this.state = state;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public void increaseFailuresCount() {
        this.failuresCount = this.getFailuresCount() + 1;
    }

    @Override
    public Integer getFailuresCount() {
        return Commons.select(failuresCount, 0);
    }

    public void setFailuresCount(Integer failuresCount) {
        this.failuresCount = failuresCount;
    }

    @Override
    public UUID getJobId() {
        return this.jobId;
    }

    @Override
    public void setJobId(UUID id) {
        this.jobId = id;
    }

    @Override
    public Date getCreated() {
        return this.created;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

}
