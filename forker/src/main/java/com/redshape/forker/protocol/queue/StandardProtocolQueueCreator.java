package com.redshape.forker.protocol.queue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/29/12
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardProtocolQueueCreator implements IProtocolQueueCreator {

    private Collection<IProtocolQueue> workQueues = new ArrayList<IProtocolQueue>();
    private Collection<IProtocolQueue> resultQueues = new ArrayList<IProtocolQueue>();


    @Override
    public IProtocolQueue createWorkQueue() {
        IProtocolQueue result;
        this.workQueues.add( result = new StandardProtocolQueue() );
        return result;
    }

    @Override
    public IProtocolQueue createResultsQueue() {
        IProtocolQueue result;
        this.resultQueues.add( result = new StandardProtocolQueue() );
        return result;
    }

}
