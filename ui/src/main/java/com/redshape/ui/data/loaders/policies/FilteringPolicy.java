package com.redshape.ui.data.loaders.policies;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventType;
import com.redshape.ui.events.IEventHandler;
import com.redshape.utils.IFilter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author nikelin
 * @date 26/04/11
 * @package com.redshape.ui.data.loaders.policies
 */
public class FilteringPolicy<T extends IModelData> implements IDataLoader<T>, IDataLoaderPolicy<T> {
    private IDataLoader<T> loader;
    private IFilter<T> filter;

    public FilteringPolicy( IDataLoader<T> loader, IFilter<T> filter ) {
        this.loader = loader;
        this.filter = filter;
    }

    public IDataLoader<T> reduce() {
        return this.loader;
    }

    @Override
    public Collection<T> preProcess(Collection<T> data) {
        Collection<T> result = new ArrayList<T>();
        for ( T record : data ) {
            if ( this.filter.filter(record) ) {
                result.add(record);
            }
        }

        return result;
    }

    @Override
    public void load() throws LoaderException {
        this.loader.load();
    }

    @Override
    public void forwardEvent(AppEvent event) {
        this.loader.forwardEvent( event );
    }

    @Override
    public void forwardEvent(EventType type, Object... args) {
        this.forwardEvent( type, args );
    }

    @Override
    public void addListener(EventType type, IEventHandler handler) {
        this.addListener( type, handler );
    }
}
