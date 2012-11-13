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

package com.redshape.jobs.managers;

import com.redshape.jobs.IJob;
import com.redshape.jobs.handlers.HandlingException;
import com.redshape.jobs.handlers.IJobHandler;
import com.redshape.jobs.result.IJobResult;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

public interface IJobsManager {

    public Map<Class<? extends IJob>, IJobHandler<?, ?>> getHandlers();

	public <T extends IJob, R extends IJobResult> Future<R> execute(T job)
				throws HandlingException;
	
	public void cancel(UUID jobId) throws HandlingException;
	
}
