/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ui.data.adapters.swing;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.stores.StoreEvents;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.events.UIEvents;
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
		

		this.filter = filter;
		this.store = store;
		
		this.init();

		if ( this.store != null ) {
			this.bindStore();
		}
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
        
        for ( int i = 0; i < this.getItemCount(); i++ ) {
            if ( item.equals( this.getItemAt(i) ) ) {
                return;
            }
        }
		
		super.addItem(item);
	}

    protected void onRecordAdd( T record ) {
        this.addItem( record );
        this.setEnabled(true);
        Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this );
    }

    protected void onStoreRefresh() {
        for ( int i = 0; i < this.getItemCount(); i++ ) {
            this.removeItemAt(i);
        }

        this.revalidate();
        this.repaint();
    }

    protected void onRecordRemoved( T record ) {
        this.removeItem( record );
        Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, ComboBoxAdapter.this );
    }
	
	protected void bindStore() {
		if ( 0 != this.store.count() ) {
			for ( T item : this.store.getList() ) {
				this.addItem( item );
			}
		} else {
            this.setEnabled(false);
        }
		
		this.store.addListener(StoreEvents.Added, new IEventHandler() {
			private static final long serialVersionUID = 882471310689793174L;

			@Override
			public void handle(AppEvent event) {
				ComboBoxAdapter.this.onRecordAdd( event.<T>getArg(0) );
			}
		});

        this.store.addListener( StoreEvents.Refresh, new IEventHandler() {
            private static final long serialVersionUID = -3661096688247159237L;

            @Override
            public void handle(AppEvent event) {
                ComboBoxAdapter.this.onStoreRefresh();
            }
        });

		this.store.addListener( StoreEvents.Refresh, new IEventHandler() {
			private static final long serialVersionUID = -3661096688247159237L;

			@Override
			public void handle(AppEvent event) {
				ComboBoxAdapter.this.onStoreRefresh();
			}
		});
		
		this.store.addListener(StoreEvents.Removed, new IEventHandler() {
			private static final long serialVersionUID = -7094208270522137221L;

			@Override
			public void handle(AppEvent event) {
				ComboBoxAdapter.this.onRecordRemoved( event.<T>getArg(0) );
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
