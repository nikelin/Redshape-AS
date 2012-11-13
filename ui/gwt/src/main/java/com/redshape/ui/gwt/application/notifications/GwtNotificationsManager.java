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
