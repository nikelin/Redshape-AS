package com.redshape.utils.events;

import java.io.Serializable;

/**
 * Interface for event handlers
 * 
 * @author nikelin
 * @param <T extends IEvent>
 */
public interface IEventListener<T extends IEvent> extends Serializable {

	public void handleEvent(T event);

}
