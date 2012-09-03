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
