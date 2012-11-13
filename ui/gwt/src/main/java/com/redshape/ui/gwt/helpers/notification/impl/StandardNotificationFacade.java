/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
