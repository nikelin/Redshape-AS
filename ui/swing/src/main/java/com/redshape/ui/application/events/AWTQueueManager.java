package com.redshape.ui.application.events;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.application.events
 * @date 2/7/12 {8:25 PM}
 */
public class AWTQueueManager implements IEventQueue {

    @Override
    public void invokeAndWait( Runnable runnable ) throws InterruptedException,
            InvocationTargetException {
        EventQueue.invokeAndWait(runnable);
    }

    @Override
    public void invokeLater(Runnable runnable) {
        EventQueue.invokeLater(runnable);
    }
}
