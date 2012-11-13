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

import com.redshape.utils.Commons;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 6/10/12
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsyncJobWrapper extends AbstractJob {

    private IJob job;

    public AsyncJobWrapper() {
        this(null);
    }

    public AsyncJobWrapper(IJob job) {
        Commons.checkNotNull(job);
        this.job = job;
    }

    public IJob getTargetJob() {
        return job;
    }

    @Override
    public UUID getJobId() {
        return job.getJobId();
    }

    @Override
    public void setJobId(UUID id) {
        if ( this.job == null ) {
            return;
        }
        job.setJobId(id);
    }

    @Override
    public void setState(JobStatus status) {
        if ( this.job == null ) {
            return;
        }
        job.setState(status);
    }

    @Override
    public JobStatus getState() {
        return job.getState();
    }

    @Override
    public void setUpdated(Date date) {
        if ( this.job == null ) {
            return;
        }
        job.setUpdated(date);
    }

    @Override
    public Date getUpdated() {
        return job.getUpdated();
    }

    @Override
    public Date getCreated() {
        return job.getCreated();
    }
}
