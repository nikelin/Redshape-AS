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

import com.redshape.jobs.IJob;
import com.redshape.jobs.result.IJobResult;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IJobExecutor<T extends IJob, V extends IJobResult> extends Remote {

	public V accept(T job) throws RemoteException;
	
	public void cancel(UUID jobId) throws RemoteException;
	
	public void pause(UUID jobId) throws RemoteException;
	
}
