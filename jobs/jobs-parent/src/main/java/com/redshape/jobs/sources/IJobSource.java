package com.redshape.jobs.sources;

import com.redshape.jobs.IJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.result.IJobResult;
import com.redshape.utils.events.IEventDispatcher;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/9/11
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IJobSource<T extends IJob> extends IEventDispatcher {
   
    public String getName();
    
    public int getUpdateInterval();
    
    public void complete( T job, IJobResult result) throws JobException;
    
    public void save( T entity ) throws JobException;

    public List<T> fetch() throws JobException;

}
