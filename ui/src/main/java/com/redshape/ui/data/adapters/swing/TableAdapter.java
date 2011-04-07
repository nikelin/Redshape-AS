package com.redshape.ui.data.adapters.swing;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.data.stores.StoreEvents;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.events.UIEvents;

public class TableAdapter<T extends IModelData> extends JTable {
	private static final long serialVersionUID = 5754270316365552129L;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( TableAdapter.class );
	
	private IStore<T> store;
	private boolean initialized;
	
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
		if ( this.initialized ) {
			return;
		}
		
		this.store.addListener( StoreEvents.Removed, new IEventHandler() {
			
			@Override
			public void handle(AppEvent event) {
				TableAdapter.this.revalidate();
				TableAdapter.this.repaint();
			}
		});
		
		this.store.addListener( StoreEvents.Added, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				TableAdapter.this.revalidate();
				TableAdapter.this.repaint();
			}
		});
		
		this.store.addListener( StoreEvents.Loaded, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				Dispatcher.get().forwardEvent( new AppEvent( UIEvents.Core.Repaint, TableAdapter.this ) );
			}
		});
		
		this.initialized = true;
	}
	
}
