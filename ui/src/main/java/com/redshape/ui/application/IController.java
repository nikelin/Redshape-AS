package com.redshape.ui.application;

import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventHandler;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 2:50
 * To change this template use File | Settings | File Templates.
 */
public interface IController extends IEventHandler {

    public Set<? extends EventType> getRegisteredEvents();

    public void addChild( IController controller );

}
