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

package com.redshape.jobs.services.execution;

import com.redshape.jobs.JobException;
import com.redshape.jobs.result.IJobResult;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IJobsDispatcher<T extends IJobResult, V extends IExecutorDescriptor> extends Remote {
	
	/**
	 * Executors registering method what each client thread should invoke
	 * to provides dispatcher such amount of information to decide what kind and amount
	 * of jobs needs to provide him.
	 *  
	 * @param descriptor Object which describes client
	 * @return Unique client identifier which will be used to create jobs and etc.
	 * @throws java.rmi.RemoteException
	 */
	public UUID register(V descriptor) throws RemoteException;

	/**
	 * Unregister client from dispatcher connections list
	 *
	 * @param executorId
	 * @throws java.rmi.RemoteException
	 */
	public void unregister(UUID executorId) throws RemoteException;

	/**
	 * Method which provides client with ability to send job execution result
	 * on dispatcher side.
	 *
	 * @param result Execution result object which contains completed job ID and other job related data
	 * @throws java.rmi.RemoteException
	 */
	public void complete(T result) throws RemoteException;

	/**
	 * Marks job under given UUID as failed and provides fail case details
	 *
	 * @param jobId
	 * @param e
	 * @throws java.rmi.RemoteException
	 */
	public void fail(UUID agentId, UUID jobId, JobException e) throws RemoteException;
	
}
