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

import java.util.Date;
import java.util.UUID;

public abstract class AbstractJob implements IJob {
	private static final long serialVersionUID = -5210141269965829207L;
	
	private Date created;
    private Date updated;
	private UUID id;
	private JobStatus state;

	public AbstractJob() {
		this( UUID.randomUUID() );
	}

	public AbstractJob( UUID jobId ) {
		this.created = new Date();

		if ( jobId != null ) {
			this.setJobId(jobId);
		}
	}

    @Override
    public void setUpdated(Date date) {
        this.updated = date;
    }

    @Override
    public Date getUpdated() {
        return this.updated;
    }

	@Override
	public void setState(JobStatus status) {
		this.state = status;
	}

	@Override
	public JobStatus getState() {
		return this.state;
	}

	@Override
	public Date getCreated() {
		return this.created;
	}
	
	protected UUID generateUUID() {
		return UUID.randomUUID();
	}
	
	@Override
	public void setJobId(UUID id) {
		this.id = id;
	}
	
	@Override
	public UUID getJobId() {
		return this.id;
	}
	
}
