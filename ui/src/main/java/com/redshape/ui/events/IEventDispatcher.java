package com.redshape.ui.events;

import com.redshape.ui.events.AppEvent;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public interface IEventDispatcher<T extends EventType> {

    public void forwardEvent( AppEvent<T> event );

    public void forwardEvent( T type, Object... args );

    public void addListener( T type, IEventHandler<T> handler );

}
