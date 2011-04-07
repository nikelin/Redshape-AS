package com.redshape.ui.data.adapters.swing;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.stores.StoreEvents;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;
import com.redshape.utils.IFilter;

/**
 * Combobox adapter to support direct data store items rendering
 * 
 * @author nikelin
 * @param <T>
 */
public class ComboBoxAdapter<T extends IModelData> extends JComboBox {
	private static final long serialVersionUID = -1398304640662810901L;

	private IStore<T> store;
	private IFilter<T> filter;
	
	/**
	 * Binds to given store without any records filtering.
	 * 
	 * @param store
	 */
	public ComboBoxAdapter( IStore<T> store ) {
		this(store, null);
	}
	
	/**
	 * Binds to store and display only that records which
	 * are admitted thought given filter.
	 * 
	 * @param store
	 * @param filter
	 */
	public ComboBoxAdapter( IStore<T> store, IFilter<T> filter ) {
		super();
		
		if ( store == null ) {
			throw new IllegalArgumentException("null");
		}
		
		this.filter = filter;
		this.store = store;
		
		this.init();
		this.bindStore();
	}
	
	protected void init() {
		this.setRenderer(new RenderGuy());
	}
	
	protected Component createDisplayComponent( T record ) {
		return new JLabel( String.valueOf(record) );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addItem( Object item ) {
		if ( this.filter != null && !this.filter.filter( (T) item ) ) {
			return;
		}
		
		super.addItem(item);
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
	
	public class RenderGuy implements ListCellRenderer, Serializable {
		private static final long serialVersionUID = -8638619364348233293L;

		@SuppressWarnings("unchecked")
		@Override
		public Component getListCellRendererComponent(JList arg0, Object arg1,
				int arg2, boolean arg3, boolean arg4) {
			return ComboBoxAdapter.this.createDisplayComponent( arg1 != null ? (T) arg1 : null );
		}
	}
	
}
