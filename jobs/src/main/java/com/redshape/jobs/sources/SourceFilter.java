package com.redshape.jobs.sources;

import com.redshape.jobs.IJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.result.IJobResult;
import com.redshape.utils.Commons;
import com.redshape.utils.IFilter;
import com.redshape.utils.events.IEvent;
import com.redshape.utils.events.IEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/28/12
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class SourceFilter<T extends IJob> implements  IJobSource<T> {

    private IJobSource<T> targetSource;
    private IFilter<T> filter;

    public SourceFilter( IJobSource<T> targetSource, IFilter<T> filter ) {
        Commons.checkNotNull(targetSource);
        Commons.checkNotNull(filter);

        this.targetSource =targetSource;
        this.filter = filter;
    }

    @Override
    public int getUpdateInterval() {
        return targetSource.getUpdateInterval();
    }

    @Override
    public void complete(T job, IJobResult result) throws JobException {
        targetSource.complete(job, result);
    }

    @Override
    public void save(T entity) throws JobException {
        targetSource.save(entity);
    }

    @Override
    public List<T> fetch() throws JobException {
        List<T> result = new ArrayList<T>();
        for ( T item : this.targetSource.fetch() ) {
            if ( this.filter.filter(item) ) {
                result.add(item);
            }
        }

        return result;
    }

    @Override
    public <Z extends IEvent> void removeEventListener(Class<Z> type, IEventListener<? extends Z> listener) {
        targetSource.removeEventListener(type, listener);
    }

    @Override
    public <Z extends IEvent> void addEventListener(Class<Z> type, IEventListener<? extends Z> listener) {
        targetSource.addEventListener(type, listener);
    }
}
