package com.redshape.daemon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteService extends Remote {

	public String getServiceName() throws RemoteException;
	
}
