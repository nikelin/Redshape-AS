package com.redshape.ui;

import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.UIEvents;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;

/**
 * @author nikelin
 */
public abstract class AbstractApplication {
    private JFrame context;

    public AbstractApplication( JFrame context ) {
        this.context = context;
    }

    public void start() throws ApplicationException {
        Dispatcher.get().addListener(UIEvents.Core.Repaint, new IEventHandler() {
            @Override
            public void handle(AppEvent type) {
                JComponent context = ( (JComponent) UIRegistry.get(UIConstants.CENTER_PANE) );
                context.revalidate();
                context.repaint();
            }
        });

        Dispatcher.get().addListener(UIEvents.Core.Init, new IEventHandler() {
            @Override
            public void handle(AppEvent type) {
                AbstractApplication.this.context.setVisible(true);
            }
        });

        Dispatcher.get().forwardEvent( UIEvents.Core.Init );


    }


}
