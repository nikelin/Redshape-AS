package com.redshape.utils.events;

import java.util.*;

/**
 * Abstract events dispatcher
 * 
 * @author nikelin
 * @see IEventListener
 * @see IEvent
 * @param <T extends IEvent>
 */
public abstract class AbstractEventDispatcher implements IEventDispatcher {
    private Map<Class<? extends IEvent>, 
    			Collection<IEventListener<IEvent>>> listeners 
    			= new HashMap<Class<? extends IEvent>, Collection<IEventListener<IEvent>>>();

    @SuppressWarnings("unchecked")
	@Override
    public <T extends IEvent> void addEventListener( Class<T> event, IEventListener<? extends T> listener ) {
        if ( this.listeners.containsKey(event) ) {
        	this.listeners.get(event).add( (IEventListener<IEvent>) listener);
        }
        
        Collection<IEventListener<IEvent>> listeners = new HashSet<IEventListener<IEvent>>();
        listeners.add( (IEventListener<IEvent>) listener );
        
        this.listeners.put(event, listeners);
    }

    @Override
    public <T extends IEvent> void removeEventListener( Class<T> type, IEventListener<? extends T> listener ) {
    	if ( !this.listeners.containsKey(type) ) {
    		return;
    	}
    	
    	this.listeners.get(type).remove(listener);
    }

    protected Collection<IEventListener<IEvent>> getEventListeners( IEvent event ) {
    	Collection<IEventListener<IEvent>> results = new HashSet<IEventListener<IEvent>>();
    	Set<Class<? extends IEvent>> events = this.listeners.keySet();
    	for ( Class<? extends IEvent> e : events ) {
    		if ( e.isAssignableFrom(event.getClass()) ) {
    			results.addAll( this.listeners.get(e) );
    		}
    	}
    	
    	return results;
    }
    
    protected <Z extends IEvent> void raiseEvent( Z event ) {
    	Collection<IEventListener<IEvent>> listeners = this.getEventListeners(event);
    	
        for ( IEventListener<IEvent> listener : listeners ) {
        	listener.handleEvent( event );
        }
    }
}
