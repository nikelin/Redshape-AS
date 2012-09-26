package com.redshape.daemon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteService extends Remote {

    public DaemonState state() throws RemoteException;

    public boolean ping() throws RemoteException;

	public String getServiceName() throws RemoteException;
	
}
