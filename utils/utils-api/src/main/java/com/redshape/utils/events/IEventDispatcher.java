package com.redshape.utils.events;


/**
 * Interface for event dispatchers
 * 
 * @author nikelin
 * @param <T>
 */
public interface IEventDispatcher {
	
	/**
	 * Remote given events listener object from listeners collection
	 * 
	 * @param type
	 * @param listener
	 */
	public <T extends IEvent> void removeEventListener(Class<T> type,
	                                                   IEventListener<? extends T> listener);
	
	/**
	 * Add new events listener to listeners collection
	 * 
	 * @param type
	 * @param listener
	 */
	public <T extends IEvent> void addEventListener(Class<T> type,
	                                                IEventListener<? extends T> listener);

}
