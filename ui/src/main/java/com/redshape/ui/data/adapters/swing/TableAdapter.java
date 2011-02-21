package com.redshape.ui.data.adapters.swing;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.LoaderException;

public class TableAdapter<T extends IModelData> extends JTable {
	private static final Logger log = Logger.getLogger( TableAdapter.class );
	private IStore<T> store;
	private boolean initialized;
	
	public TableAdapter( IStore<T> store ) throws LoaderException {
		this.store = store;
		
		if ( this.store != null ) {
			this.bindStore();
		}
		
		this.setModel( new TableModelAdapter<T>(this.store) );
	}
	
	protected void bindStore() throws LoaderException {		
		if ( this.initialized ) {
			return;
		}
		
		this.store.load();
		
		this.initialized = true;
	}
	
}
