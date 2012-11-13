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

package com.redshape.jobs.services;

import com.redshape.jobs.IJob;
import com.redshape.jobs.result.IJobResult;
import com.redshape.jobs.services.execution.IExecutorDescriptor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface IJobsDispatcherService<T extends IJob, V extends IJobResult> extends Remote {
	
	/**
	 * Get currently connected execution agents
	 * @return
	 */
	public Map<UUID, IExecutorDescriptor> getConnectedExecutors() throws RemoteException;
	
	/**
	 * Get currently processing jobs
	 * @return
	 */
	public Map<UUID, T> getJobs(UUID agentId) throws RemoteException;
	
	public V scheduleJob(T job) throws RemoteException;
	
	public V scheduleJob(T job, Date date) throws RemoteException;
	
	public void cancelJob(UUID job) throws RemoteException;
	
	public boolean isComplete(UUID job) throws RemoteException;
	
	public boolean isFailed(UUID job) throws RemoteException;
	
}
