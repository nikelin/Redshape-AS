package com.redshape.ui.gwt.application.notifications;

import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.notifications.INotificationsManager;
import com.redshape.ui.application.notifications.NotificationType;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt.application.notifications
 * @date 2/9/12 {7:52 PM}
 */
public class GwtNotificationsManager implements INotificationsManager {

    @Override
    public String request(String message) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void ask(String message, IEventHandler yesCase, IEventHandler noCase) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void warn(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void warn(String message, NotificationType type) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void info(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void info(String message, NotificationType type) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void error(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void error(String message, NotificationType type) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
