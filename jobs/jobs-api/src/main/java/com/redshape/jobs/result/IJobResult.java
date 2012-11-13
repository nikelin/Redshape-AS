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

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface IJobResult extends Serializable {

	public <T> Map<JobResultAttribute, T> getAttributes();

	public <T> T getAttribute( JobResultAttribute attribute );

	public void setAttribute( JobResultAttribute attribute, Object value );

	public UUID getJobId();
	
	public boolean isFinished();
	
	public Integer getProgress();

	public void setCompletionDate(Date date);

	public Date getCompletionDate();

	public JobStatus getStatus();
}
