package com.redshape.ui.application.events;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.application.events
 * @date 2/7/12 {6:47 PM}
 */
public interface IEventQueue {
    
    public void invokeAndWait( Runnable runnable );
    
    public void invokeLater( Runnable runnable );
    
}
