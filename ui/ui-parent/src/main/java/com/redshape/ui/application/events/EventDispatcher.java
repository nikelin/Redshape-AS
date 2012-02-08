package com.redshape.ui.application.events;

import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.Commons;

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
public class EventDispatcher implements IEventDispatcher {
    private Map<EventType, Collection<IEventHandler>> listeners = 
    				new HashMap<EventType, Collection<IEventHandler>>();

    public void addListener( EventType type, IEventHandler handler ) {
        if ( !this.listeners.containsKey(type) ) {
            this.listeners.put( type, new HashSet<IEventHandler>() );
        }

        this.listeners.get(type).add( handler );
    }

    public void forwardEvent( EventType type ) {
        this.forwardEvent( type, new Object[] {}  );
    }

    public void forwardEvent( EventType type, Object... args ) {
        this.forwardEvent( new AppEvent( type, args  ) );
    }

    public void forwardEvent( final AppEvent event ) {
        Commons.checkNotNull(event, "Non-null event expected");
        
        Collection<IEventHandler> handlers = this.listeners.get( event.getType() );
        if ( handlers != null && !handlers.isEmpty() ) {
            for ( final IEventHandler handler : handlers ) {
                UIRegistry.getEventQueue().invokeLater(
                        new Runnable() {
                            @Override
                            public void run() {
                                handler.handle(event);
                            }
                        }
                );
            }
        }
    }

}
