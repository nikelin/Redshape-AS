package com.redshape.utils.streams;

import com.redshape.utils.TimeSpan;

import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:09 PM}
 */
public interface IStreamWaiter {

    public void setPriority( Priority priority );
    
    public void await() throws IOException;

    public void awaitUntil( TimeSpan span ) throws InterruptedException, IOException;

    public void addEventHandler( IStreamEventHandler handler );

    public void removeEventHandler( IStreamEventHandler handler );

}
