package com.redshape.ui.data.stores;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelType;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.ModelEvent;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderEvents;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.utils.IFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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

    private Object lock = new Object();

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
				private static final long serialVersionUID = -177894475768338821L;

				@Override
				public void handle(AppEvent event) {
					ListStore.this.forwardEvent( StoreEvents.Refresh );
				}
			}
		);
	}
	
	@Override
	public boolean isEmpty() {
		return this.count() == 0;
	}
    
    @Override
    public void clear() {
        synchronized (lock) {
            this.records.clear();
            this.forwardEvent( StoreEvents.Clean );
        }
    }

    protected void bindRecord( final V record ) {
		record.addListener( ModelEvent.REMOVED, new IEventHandler() {
			private static final long serialVersionUID = 1393458864711926241L;

			@Override
			public void handle(AppEvent event) {
				ListStore.this.remove( record );
			}
		});

    	record.addListener(ModelEvent.CHANGED, new IEventHandler() {
			private static final long serialVersionUID = -8662467432503234458L;

			@Override
			public void handle(AppEvent event) {
				ListStore.this.forwardEvent( StoreEvents.Changed, event.getArg(0), record );
			}
		});
    }
    
    protected void bindLoader( IDataLoader<V> loader ) {
        loader.addListener(LoaderEvents.Loaded, new IEventHandler() {
			private static final long serialVersionUID = 6612096418407552533L;

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
	public V findRecord(IFilter<V> filter) {
    	if ( filter == null ) {
    		throw new IllegalArgumentException("null");
    	}
    	
    	for ( V record : this.getList() ) {
    		if ( filter.filter(record) ) {
    			return record;
    		}
    	}
    	
    	return null;
	}

	@Override
	public Collection<V> find(IFilter<V> filter) {
		if ( filter == null ) {
			throw new IllegalArgumentException("null");
		}
		
		Collection<V> result = new HashSet<V>();
		for ( V record : this.getList() ) {
			if ( filter.filter( record ) ) {
				result.add( record );
			}
		}
		
		return result;
	}

	@Override
	public boolean filter(IFilter<V> filter) {
		if ( filter == null ) {
			throw new IllegalArgumentException("null");
		}
		
		boolean result = !this.isEmpty();
		for ( V record : this.getList() ) {
			result = result && filter.filter( record );
		}
		
		return result;
	}

	@Override
    public void add( V record ) {
        synchronized ( lock ) {
            this.bindRecord(record);
            this.records.add(record);
            this.forwardEvent( StoreEvents.Added, record, this.count() );
        }
    }

    @Override
    public List<V> getList() {
        return this.records;
    }

    @Override
    public void remove( V item ) {
        synchronized ( lock ) {
            int index = this.records.indexOf(item);
            this.records.remove(item);
            this.forwardEvent( StoreEvents.Removed, item, index );
        }
    }

    @Override
    public void removeAt( int index ) {
        this.remove( this.records.get(index) );
    }

    protected IDataLoader<V> getLoader() {
        return this.loader;
    }

    public void load() throws LoaderException {
        synchronized( lock ) {
            this.clear();

            if ( this.loader != null ) {
                this.loader.load();
            }
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
