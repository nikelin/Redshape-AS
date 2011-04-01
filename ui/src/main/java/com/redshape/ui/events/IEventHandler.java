package com.redshape.ui.events;

import com.redshape.ui.events.AppEvent;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public interface IEventHandler {

    public void handle( AppEvent event );

}
