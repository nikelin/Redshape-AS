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

package com.redshape.ui.gwt.views.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.ui.gwt.helpers.notification.Notification;
import com.redshape.utils.Commons;

/**
 * Created with IntelliJ IDEA.
 * User: nakham
 * Date: 30.08.12
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class NotificationWidget extends PopupPanel {

    public interface Binder extends UiBinder<Widget, NotificationWidget> {

    }

    public static final Binder binder = GWT.create(Binder.class);

    private Notification notification;

    @UiField
    Label titleField;

    @UiField
    Label messageField;

    @UiField
    Button closeButton;

    public NotificationWidget(Notification notification) {
        Commons.checkNotNull(notification);

        this.add(binder.createAndBindUi(this));

        this.notification = notification;
        this.init();
    }

    protected String getNotificationTitle() {
        switch ( this.notification.getType() ) {
            case ERROR:
                return "Ошибка";
            case INFO:
                return "Информация";
            case WARNING:
                return "Предупреждение";
            default:
                throw new IllegalStateException("Unknown notification type");
        }
    }

    protected void init() {
        this.titleField.setText( this.getNotificationTitle());
        this.messageField.setText( this.notification.getMessage() );
    }
    
    @UiHandler("closeButton")
    public void handleCloseButton( ClickEvent event ) {
        this.hide();
    }

}
