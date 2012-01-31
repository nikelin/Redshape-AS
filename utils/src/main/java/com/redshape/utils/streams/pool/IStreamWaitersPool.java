package com.redshape.utils.streams.pool;

import com.redshape.utils.TimeSpan;
import com.redshape.utils.streams.IStreamWaiter;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:11 PM}
 */
public interface IStreamWaitersPool {

    public void addWaiter( IStreamWaiter waiter );

    public void removeWaiter( IStreamWaiter waiter );

    public List<IStreamWaiter> getWaiters();

    public void clearWaiters();

    public void await();

    public void awaitUntil( TimeSpan span ) throws InterruptedException;

}
