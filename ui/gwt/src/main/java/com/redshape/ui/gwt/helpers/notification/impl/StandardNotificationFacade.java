package com.redshape.ui.gwt.helpers.notification.impl;


import com.redshape.ui.gwt.helpers.notification.INotificationFacade;
import com.redshape.ui.gwt.helpers.notification.Notification;
import com.redshape.ui.gwt.views.widgets.NotificationWidget;

/**
 * Created with IntelliJ IDEA.
 * User: max.gura
 * Date: 23.08.12
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */
public final class StandardNotificationFacade implements INotificationFacade {

    @Override
    public void info( String message )  {
        this.createWidget(Notification.createInfo(message))
            .show();
    }

    @Override
    public void error(String message)  {
        this.error(null, message);
    }


    @Override
    public void error(Throwable caught) {
        this.error(caught, caught.getMessage());
    }

    @Override
    public void error(Throwable caught, String message)  {
        this.createWidget(Notification.createError(message, caught))
            .show();
    }

    @Override
    public void alert( String message )  {
        this.createWidget(Notification.createAlert(message))
            .show();
    }

    protected NotificationWidget createWidget( Notification notification ) {
        return new NotificationWidget(notification);
    }


}
