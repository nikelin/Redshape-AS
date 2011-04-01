package com.redshape.ui.data.adapters.swing;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.events.data.stores.StoreEvents;

public class ComboBoxAdapter<T extends IModelData> extends JComboBox {
	private static final long serialVersionUID = -1398304640662810901L;

	private IStore<T> store;
	
	public ComboBoxAdapter( IStore<T> store ) {
		super();
		
		if ( store == null ) {
			throw new IllegalArgumentException("null");
		}
		
		this.store = store;
		
		this.init();
		this.bindStore();
	}
	
	protected void init() {
		this.setRenderer(new ListCellRenderer() {
			@SuppressWarnings("unchecked")
			@Override
			public Component getListCellRendererComponent(JList arg0, Object arg1,
					int arg2, boolean arg3, boolean arg4) {
				return ComboBoxAdapter.this.createDisplayComponent( (T) arg1);
			}
		});
	}
	
	protected Component createDisplayComponent( T record ) {
		return new JLabel( record.toString() );
	}
	
	protected void bindStore() {
		if ( 0 != this.store.count() ) {
			for ( T item : this.store.getList() ) {
				this.addItem( item );
			}
		}
		
		this.store.addListener(StoreEvents.Added, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				ComboBoxAdapter.this.addItem( event.<T>getArg(0) );
			}
		});
		
		this.store.addListener(StoreEvents.Removed, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				ComboBoxAdapter.this.removeItem( event.<T>getArg(0) );
			}
		});
	}
	
}
