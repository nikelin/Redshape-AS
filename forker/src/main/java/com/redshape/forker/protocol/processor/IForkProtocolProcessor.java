package com.redshape.forker.protocol.processor;

import com.redshape.forker.protocol.queue.IProtocolQueue;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/29/12
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IForkProtocolProcessor extends Runnable {

    public void stop();

    public boolean isStarted();

    public IProtocolQueue getResultsQueue();

    public IProtocolQueue getWorkQueue();

}
