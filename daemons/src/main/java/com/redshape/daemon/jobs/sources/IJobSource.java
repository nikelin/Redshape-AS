package com.redshape.daemon.jobs.sources;

import com.redshape.daemon.jobs.IJob;
import com.redshape.daemon.jobs.result.IJobResult;
import com.redshape.utils.events.IEventDispatcher;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/9/11
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IJobSource extends IEventDispatcher {

    public void complete( IJob job, IJobResult result);

}
