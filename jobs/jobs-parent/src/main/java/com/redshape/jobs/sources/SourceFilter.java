package com.redshape.jobs.sources;

import com.redshape.jobs.IJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.result.IJobResult;
import com.redshape.persistence.entities.DtoUtils;
import com.redshape.persistence.entities.IDTO;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;
import com.redshape.utils.IFilter;
import com.redshape.utils.events.IEvent;
import com.redshape.utils.events.IEventListener;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/28/12
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class SourceFilter<T extends IJob> implements  IJobSource<T> {
    private static final Logger log = Logger.getLogger(SourceFilter.class);

    private String name;
    private IJobSource<T> targetSource;
    private IFilter<Class<? extends T>> filter;

    public SourceFilter( String name, IJobSource<T> targetSource, IFilter<Class<? extends T>> filter ) {
        Commons.checkNotNull(targetSource);
        Commons.checkNotNull(filter);

        
        this.name = name;
        this.targetSource =targetSource;
        this.filter = filter;
    }

    @Override
    public int getResultAwaitDelay() {
        return this.targetSource.getResultAwaitDelay();
    }

    @Override
    public String getName() {
        return this.name;
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
    public T save(T entity) throws JobException {
        return targetSource.save(entity);
    }

    @Override
    public void asyncRun(T job) throws JobException {
        targetSource.asyncRun(job);
    }

    @Override
    public List<T> fetch() throws JobException {
        List<T> result = new ArrayList<T>();
        Collection<T> filteringSet = this.targetSource.fetch();
        log.info("Going to filter working set of " + filteringSet.size() + " elements...");
        for ( T item : filteringSet ) {
            if ( this.filter == null || !this.filter.filter( (Class<T>) item.getClass()) ) {
                continue;
            }

            if ( item instanceof IDTO) {
                log.info("Hydrating DTO object with a type of " + item.getClass().getCanonicalName() + "...");
                item = (T) DtoUtils.fromDTO( (IEntity) item );
            }

            result.add(item);
        }

        log.info("Working set size after filtering: " + result.size() + "...");
        
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
