package com.redshape.ui.events;

import com.redshape.ui.events.AppEvent;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public interface IEventDispatcher {

    public void forwardEvent( AppEvent event );

    public void forwardEvent( EventType type, Object... args );

    public void addListener( EventType type, IEventHandler handler );

}
