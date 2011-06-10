package com.redshape.ui.application.handlers.awt;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.UIEvents;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 23.05.11
 * Time: 2:16
 * To change this template use File | Settings | File Templates.
 */
public class AWTExceptionsHandler {

    public static void register() {
        System.setProperty("sun.awt.exception.handler", AWTExceptionsHandler.class.getName() );
    }

    public void handle(Throwable e) {
        Dispatcher.get().forwardEvent( UIEvents.Core.Error, e );
    }
}
