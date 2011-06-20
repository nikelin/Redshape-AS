package com.redshape.daemon.services;

import com.redshape.daemon.jobs.IJob;
import com.redshape.daemon.jobs.execution.IExecutorDescriptor;
import com.redshape.daemon.jobs.result.IJobResult;

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
