package com.redshape.utils.config.watchers;

import com.redshape.utils.config.sources.IConfigSource;
import com.redshape.utils.events.IEventDispatcher;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/2/12
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISourceWatcher extends IEventDispatcher {

    public void addWatchable( IConfigSource source );

    public void removeWatchable( IConfigSource source );

    public Collection<IConfigSource> getWatchableList();

    public void clearWatchableList();

    public void stop();

    public void start();

}
