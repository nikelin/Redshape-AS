package com.redshape.ui.application.events;

import java.awt.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.application.events
 * @date 2/7/12 {8:25 PM}
 */
public class AWTQueueManager implements IEventQueue {

    @Override
    public void invokeAndWait( Runnable runnable ) {
        try {
            EventQueue.invokeAndWait(runnable);
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    @Override
    public void invokeLater(Runnable runnable) {
        EventQueue.invokeLater(runnable);
    }
}
