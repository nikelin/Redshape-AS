package com.redshape.daemon.jobs.sources;

import com.redshape.daemon.jobs.IJob;
import com.redshape.daemon.jobs.JobException;
import com.redshape.utils.events.IEventDispatcher;

import java.util.List;

/**
 * @package com.redshape.daemon.jobs.sources
 * @user cyril
 * @date 6/21/11 5:51 PM
 */
public interface IDAOJobSource<T extends IJob> extends IEventDispatcher {

    public int getPeriod();

    public int getChunkSize();

    public void save( T entity ) throws JobException;

    public List<T> fetch() throws JobException;

}
