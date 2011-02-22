package com.redshape.ui.data.stores;

import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventDispatcher;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelType;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderEvents;
import com.redshape.ui.data.loaders.LoaderException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class ListStore<V extends IModelData> extends EventDispatcher implements IStore<V> {
    private List<V> records = new ArrayList<V>();
    private IDataLoader<V> loader;
    private IModelType type;

    public ListStore( IModelType type, IDataLoader<V> loader ) {
        this.loader = loader;
        this.type = type;

        if ( this.loader != null ) {
        	this.bindLoader(loader);
        }
    }
    
    @Override
    // @TODO: add events dispatching
    public void clear() {
    	this.records.clear();
    }

    protected void bindLoader( IDataLoader<V> loader ) {
    	loader.bind(this);
    	
        loader.addListener(LoaderEvents.Loaded, new IEventHandler() {
            @Override
            public void handle( AppEvent type) {
                for ( V record : type.<Collection<V>>getArg(0) ) {
                    ListStore.this.add(record);
                }
            }
        });
    }

    @Override
    public V getAt( int index ) {
        return this.records.get(index);
    }

    @Override
    public IModelType getType() {
        return this.type;
    }

    @Override
    public int count() {
        return this.records.size();
    }

    @Override
    public void add( V record ) {
        this.records.add(record);
    }

    @Override
    public List<V> getList() {
        return this.records;
    }

    @Override
    public void remove( V item ) {
        this.records.remove(item);
        this.forwardEvent( StoreEvents.Removed, item );
    }

    @Override
    public void removeAt( int index ) {
        this.remove( this.records.get(index) );
    }

    protected IDataLoader<V> getLoader() {
        return this.loader;
    }

    public void load() throws LoaderException {
        this.clear();
        this.loader.load();
    }
}
