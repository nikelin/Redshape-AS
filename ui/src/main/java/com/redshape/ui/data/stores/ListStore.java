package com.redshape.ui.data.stores;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventDispatcher;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelType;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.ModelEvent;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderEvents;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.events.UIEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Simple list-based store implementation
 *
 * @author nikelin
 */
public class ListStore<V extends IModelData>
						extends EventDispatcher
						implements IStore<V> {
	private static final long serialVersionUID = 1006177585211430914L;
	
	private List<V> records = new ArrayList<V>();
    private IDataLoader<V> loader;
    private IModelType type;

    public ListStore( IModelType type ) {
    	this( type, null );
    }
    
    public ListStore( IModelType type, IDataLoader<V> loader ) {
        this.loader = loader;
        this.type = type;

		this.init();
    }

	protected void init() {
		if ( this.loader != null ) {
			this.bindLoader(loader);
		}

		Dispatcher.get().addListener(
			UIEvents.Core.Refresh.Stores,
			new IEventHandler() {
				@Override
				public void handle(AppEvent event) {
					ListStore.this.forwardEvent( StoreEvents.Refresh );
				}
			}
		);
	}
    
    @Override
    public void clear() {
    	this.records.clear();
    	this.forwardEvent( StoreEvents.Clean );
    }

    protected void bindRecord( final V record ) {
		record.addListener( ModelEvent.REMOVED, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				ListStore.this.remove( record );
			}
		});

    	record.addListener(ModelEvent.CHANGED, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				ListStore.this.forwardEvent( StoreEvents.Changed, event.getArg(0), record );
			}
		});
    }
    
    protected void bindLoader( IDataLoader<V> loader ) {
        loader.addListener(LoaderEvents.Loaded, new IEventHandler() {
            @Override
            public void handle( AppEvent type) {
            	ListStore.this.clear();
            	
                for ( V record : type.<Collection<V>>getArg(0) ) {
                    ListStore.this.add(record);
                }
                
                ListStore.this.forwardEvent( StoreEvents.Loaded );
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
    	this.bindRecord(record);
        this.records.add(record);
        this.forwardEvent( StoreEvents.Added, record );
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
    	V item;
        this.remove( item = this.records.get(index) );
        this.forwardEvent( StoreEvents.Removed, item );
    }

    protected IDataLoader<V> getLoader() {
        return this.loader;
    }

    public void load() throws LoaderException {
        this.clear();
        
        if ( this.loader != null ) {
        	this.loader.load();
        }
    }

	public void setRecords( List<V> records ) {
		this.records = records;
	}

	@Override
	public void setLoader(IDataLoader<V> loader) {
		this.loader = loader;
		this.bindLoader(loader);
	}
}
