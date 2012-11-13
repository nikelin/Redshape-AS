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

package com.redshape.ui.application.status;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/14/11
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class StandardStatusBar implements IStatusBar {

    @Override
    public void status(String status) {
        Container component = UIRegistry.get(UIConstants.Area.SOUTH);
        component.removeAll();
        component.add(new JLabel(status));

        Dispatcher.get().forwardEvent(UIEvents.Core.Repaint, component);
    }
}
