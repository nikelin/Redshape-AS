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

package com.redshape.utils.events;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract events dispatcher
 * 
 * @author nikelin
 * @see IEventListener
 * @see IEvent
 */
public abstract class AbstractEventDispatcher implements IEventDispatcher {
    private Map<Class<? extends IEvent>, 
    			Collection<IEventListener<IEvent>>> listeners 
    			= new HashMap<Class<? extends IEvent>, Collection<IEventListener<IEvent>>>();

    private ExecutorService listenersActivator = Executors.newFixedThreadPool(150);

    protected AbstractEventDispatcher() {
        this.listenersActivator = Executors.newFixedThreadPool(150);
    }

    protected ExecutorService getListenersActivator() {
        return listenersActivator;
    }

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
    
    protected <Z extends IEvent> void raiseEvent( final Z event ) {
    	Collection<IEventListener<IEvent>> listeners = this.getEventListeners(event);

        for ( final IEventListener<IEvent> listener : listeners ) {
            this.getListenersActivator().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        listener.handleEvent(event);
                    }
                }
            );
        }
    }
}
