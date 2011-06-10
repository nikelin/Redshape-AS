package com.redshape.ui.data.adapters.swing;

import javax.swing.JTable;

import com.redshape.ui.Dispatcher;
import org.apache.log4j.Logger;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.data.stores.StoreEvents;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.events.UIEvents;

public class TableAdapter<T extends IModelData> extends JTable {
	private static final long serialVersionUID = 5754270316365552129L;
	private static final Logger log = Logger.getLogger( TableAdapter.class );
	
	private IStore<T> store;
	
	public TableAdapter( IStore<T> store ) {
		super( new TableModelAdapter<T>(store), new TableColumnModelAdapter( store.getType() ) );
		
		this.store = store;
		this.bindStore();
	}
	
	public void init() throws LoaderException {
		this.store.load();
	}
	
	protected IStore<T> getStore() {
		return this.store;
	}
	
	protected void bindStore() {
		this.store.addListener( StoreEvents.Removed, new IEventHandler() {
			private static final long serialVersionUID = 1531489734246981259L;

			@Override
			public void handle(AppEvent event) {
				log.error("Table invalidation");
				TableAdapter.this.revalidate();
				TableAdapter.this.invalidate();
				Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, TableAdapter.this );
			}
		});
		
		this.store.addListener( StoreEvents.Added, new IEventHandler() {
			private static final long serialVersionUID = 8762081158206925017L;

			@Override
			public void handle(AppEvent event) {
				TableAdapter.this.revalidate();
				Dispatcher.get().forwardEvent(UIEvents.Core.Repaint, TableAdapter.this);
			}
		});
		
		this.store.addListener( StoreEvents.Loaded, new IEventHandler() {
			private static final long serialVersionUID = -6453297309844783711L;

			@Override
			public void handle(AppEvent event) {
				Dispatcher.get().forwardEvent( new AppEvent( UIEvents.Core.Repaint, TableAdapter.this ) );
			}
		});

		this.store.addListener( StoreEvents.Refresh, new IEventHandler() {
			private static final long serialVersionUID = 5376303758395033237L;

			@Override
			public void handle(AppEvent event) {
				Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, TableAdapter.this );
			}
		});
	}

    public T getSelectedRecord() {
    	if ( this.store.isEmpty() ) {
    		return null;
    	}
    	
        return this.store.getAt( this.getSelectedRow() );
    }
	
}
