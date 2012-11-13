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

package com.redshape.jobs.result;

import com.redshape.jobs.JobStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobResult implements IJobResult {
	private static final long serialVersionUID = 640853501807730074L;
	
	private UUID jobId;
	private Date date;
	private JobStatus status;
	private Map<JobResultAttribute, Object> attributes = new HashMap<JobResultAttribute, Object>();
	
	public JobResult( UUID jobId ) {
		this.jobId = jobId;
		this.date = new Date();
	}

	@Override
	public <T> Map<JobResultAttribute, T> getAttributes() {
		return (Map<JobResultAttribute, T>) this.attributes;
	}

	@Override
	public <T> T getAttribute(JobResultAttribute attribute) {
		return (T) this.attributes.get(attribute);
	}

	@Override
	public void setAttribute(JobResultAttribute attribute, Object value) {
		this.attributes.put( attribute, value );
	}

	@Override
	public UUID getJobId() {
		return this.jobId;
	}

	@Override
	public Date getCompletionDate() {
		return this.date;
	}
	
	@Override
	public JobStatus getStatus() {
		return this.status;
	}

	@Override
	public void setCompletionDate(Date date) {
		this.date = date;
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public Integer getProgress() {
		return null;
	}
	
}
