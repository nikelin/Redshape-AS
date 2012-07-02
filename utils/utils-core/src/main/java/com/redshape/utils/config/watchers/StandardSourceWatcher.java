package com.redshape.utils.config.watchers;

import com.redshape.utils.Commons;
import com.redshape.utils.config.sources.IConfigSource;
import com.redshape.utils.events.AbstractEventDispatcher;
import com.redshape.utils.events.IEvent;
import com.redshape.utils.events.IEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/2/12
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardSourceWatcher extends AbstractEventDispatcher implements ISourceWatcher, Runnable {
    public static final int DEFAULT_BACKLOG_SIZE = 40;

    public class RefreshTask implements Runnable {

        private IConfigSource source;

        public RefreshTask( IConfigSource source ) {
            this.source = source;
        }

        @Override
        public void run() {
            if ( !this.source.isChanged() ) {
                return;
            }

            this.source.reload();
            this.source.markClean();
        }
    }

    private long ticksTime;
    private long ticksDelay;
    private ScheduledExecutorService service;
    private ExecutorService tasksService;
    private Collection<IConfigSource> source = new HashSet<IConfigSource>();

    public StandardSourceWatcher( long ticksDelay, long ticksTime ) {
        this(ticksDelay, ticksTime, new ArrayList<IConfigSource>() );
    }

    public StandardSourceWatcher( long ticksDelay, long ticksTime, Collection<IConfigSource> sources ) {
        this(ticksDelay, ticksTime, sources, Executors.newFixedThreadPool(DEFAULT_BACKLOG_SIZE));
    }

    public StandardSourceWatcher( long ticksDelay, long ticksTime, Collection<IConfigSource> sources, ExecutorService service) {
        Commons.checkNotNull(service);

        this.source = sources;
        this.ticksTime = ticksTime;
        this.ticksDelay = ticksDelay;
        this.service = this.createScheduleService();
        this.tasksService = service;

        this.start();
    }

    protected ScheduledExecutorService createScheduleService() {
        return Executors.newScheduledThreadPool(1);
    }

    @Override
    public void addWatchable(IConfigSource source) {
        this.source.add( source );
    }

    @Override
    public void removeWatchable(IConfigSource source) {
        this.source.remove(source);
    }

    @Override
    public Collection<IConfigSource> getWatchableList() {
        return this.source;
    }

    @Override
    public void clearWatchableList() {
        this.source.clear();
    }

    @Override
    public void stop() {
        this.service.shutdownNow();
    }

    protected void schedule() {
        this.service.scheduleAtFixedRate(this, this.ticksDelay, this.ticksTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        for ( IConfigSource source : this.getWatchableList() ) {
            this.tasksService.submit( new RefreshTask(source) );
        }

        this.schedule();
    }

    @Override
    public void start() {
        if ( this.service.isShutdown() ) {
            this.service = this.createScheduleService();
        }

        this.schedule();
    }
}
