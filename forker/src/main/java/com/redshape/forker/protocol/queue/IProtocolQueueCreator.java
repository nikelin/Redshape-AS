package com.redshape.forker.protocol.queue;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/29/12
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocolQueueCreator {

    public IProtocolQueue createWorkQueue();

    public IProtocolQueue createResultsQueue();

}
