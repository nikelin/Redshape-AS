package com.redshape.utils.events;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Interface for event dispatchers which exists in RMI environment
 * 
 * @author nikelin
 * @param <T>
 */
public interface IRemoteEventDispatcher extends Serializable {
	
	/**
	 * Remote given events listener object from listeners collection
	 * 
	 * @param type
	 * @param listener
	 */
	public <T extends IEvent> void removeEventListener(Class<T> type,
	                                                   IEventListener<? extends T> listener)
							throws RemoteException;
	
	/**
	 * Add new events listener to listeners collection
	 * 
	 * @param type
	 * @param listener
	 */
	public <T extends IEvent> void addEventListener(Class<T> type,
	                                                IEventListener<? extends T> listener)
							throws RemoteException;

}
