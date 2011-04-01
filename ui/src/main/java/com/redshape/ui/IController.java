package com.redshape.ui;

import com.redshape.ui.events.EventType;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.views.IView;

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
