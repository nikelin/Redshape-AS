/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.jobs.sources;

import com.redshape.jobs.IJob;
import com.redshape.jobs.JobException;
import com.redshape.jobs.result.IJobResult;
import com.redshape.utils.events.IEvent;
import com.redshape.utils.events.IEventListener;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/27/12
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummySource<T extends IJob> implements IJobSource<T> {

    private String name;
    private int updateInterval;
    private int workChunkSize;
    private int resultAwaitDelay;

    private Stack<T> scheduled = new Stack<T>();
    private Map<T, IJobResult> completed = new HashMap<T, IJobResult>();

    public DummySource(String name, int updateInterval, int resultAwaitDelay, int workChunkSize) {
        this.name = name;

        this.workChunkSize = workChunkSize;
        this.resultAwaitDelay = resultAwaitDelay;
        this.updateInterval = updateInterval;
    }

    @Override
    public int getResultAwaitDelay() {
        return this.resultAwaitDelay;
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
            deque.add( this.scheduled.pop() );
        }

        return deque;
    }

    @Override
    public <T extends IEvent> void removeEventListener(Class<T> type, IEventListener<? extends T> listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends IEvent> void addEventListener(Class<T> type, IEventListener<? extends T> listener) {
        throw new UnsupportedOperationException();
    }
}
