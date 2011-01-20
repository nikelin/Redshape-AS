package com.redshape.ui.events;

import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.events.AppEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class EventDispatcher<T extends EventType> {
    private Map<T, Collection<IEventHandler<T>>> listeners = new HashMap<T, Collection<IEventHandler<T>>>();

    public void addListener( T type, IEventHandler<T> handler ) {
        if ( !this.listeners.containsKey(type) ) {
            this.listeners.put( type, new HashSet<IEventHandler<T>>() );
        }

        this.listeners.get(type).add( handler );
    }

    public void forwardEvent( T type ) {
        this.forwardEvent( type, new Object[] {}  );
    }

    public void forwardEvent( T type, Object... args ) {
        this.forwardEvent( new AppEvent( type, args  ) );
    }

    public void forwardEvent( AppEvent<T> event ) {
        Collection<IEventHandler<T>> handlers = this.listeners.get( event.getType() );
        if ( handlers != null && !handlers.isEmpty() ) {
            for ( IEventHandler<T> handler : handlers ) {
                handler.handle( event );
            }
        }
    }

}
