package com.redshape.ui.data.loaders.policies;

import com.redshape.ui.UnhandledUIException;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventType;
import com.redshape.ui.events.IEventHandler;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 4/24/11
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class RefreshPolicy<T extends IModelData> implements IDataLoader<T>, IDataLoaderPolicy<T> {
    private static final Logger log = Logger.getLogger(RefreshPolicy.class);

    private IDataLoader loader;
    private Integer interval;
    private Timer timer = new Timer();

    public RefreshPolicy( IDataLoader loader, Integer refreshInterval ) {
        this.loader = loader;
        this.interval = refreshInterval;

        this.init();
    }

    @Override
    public Collection<T> preProcess( Collection<T> data ) {
        return this.loader.preProcess(data);
    }

    @Override
    public IDataLoader<T> reduce() {
        return this.loader;
    }

    protected Integer getInterval() {
        return this.interval;
    }

    protected Timer getTimer() {
        return this.timer;
    }

    protected void init() {
        this.getTimer().scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("Invocation refresh...");
                    RefreshPolicy.this.load();
                } catch ( Throwable e ) {
                    throw new UnhandledUIException( e.getMessage(), e );
                }
            }
        }, 0, this.getInterval() );
    }

    @Override
    public void load() throws LoaderException {
        this.loader.load();
    }

    @Override
    public void forwardEvent(AppEvent event) {
        this.loader.forwardEvent(event);
    }

    @Override
    public void forwardEvent(EventType type, Object... args) {
        this.loader.forwardEvent(type, args);
    }

    @Override
    public void addListener(EventType type, IEventHandler handler) {
        this.loader.addListener(type, handler);
    }
}
