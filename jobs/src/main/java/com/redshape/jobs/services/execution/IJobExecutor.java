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
