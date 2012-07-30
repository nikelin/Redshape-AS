package com.redshape.jobs.sources;

import com.redshape.jobs.IJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.result.IJobResult;
import com.redshape.utils.events.AbstractEventDispatcher;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/27/12
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummySource<T extends IJob> extends AbstractEventDispatcher implements IJobSource<T> {

    private String name;
    private int updateInterval;
    private int workChunkSize;

    private Queue<T> scheduled = new LinkedBlockingQueue<T>();
    private Map<T, IJobResult> completed = new HashMap<T, IJobResult>();

    public DummySource(String name, int updateInterval, int workChunkSize) {
        this.name = name;
        this.workChunkSize = workChunkSize;
        this.updateInterval = updateInterval;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getUpdateInterval() {
        return this.updateInterval;
    }

    @Override
    public void complete(T job, IJobResult result) throws JobException {
        this.completed.put(job, result);
    }

    @Override
    public void asyncRun(T job) throws JobException {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public T save(T entity) throws JobException {
        this.scheduled.add( entity );
        return entity;
    }

    @Override
    public List<T> fetch() throws JobException {
        List<T> deque = new ArrayList<T>();
        for ( int i = 0; i < workChunkSize; i++ ) {
            deque.add( this.scheduled.poll() );
        }

        return deque;
    }

}
