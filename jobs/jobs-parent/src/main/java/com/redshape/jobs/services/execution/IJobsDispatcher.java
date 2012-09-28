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
