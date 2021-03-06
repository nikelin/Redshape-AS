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

package com.redshape.ui.data.stores;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class AbstractStoresManager implements IStoresManager {
	private Collection<StoreContext> contexts = new HashSet<StoreContext>();
	private StoreContext globalContext = new StoreContext(this);

	public boolean isRegistered( Object context ) {
		for ( StoreContext registered : this.contexts ) {
			if ( registered.getContext().equals(context) ) {
				return true;
			}
		}

		return false;
	}

	protected StoreContext getContext( Object context ) {
		return this.getContext( context, false );
	}

	protected StoreContext getContext( Object context, boolean forceCreation ) {
		for ( StoreContext registered : this.contexts ) {
			if ( registered.getContext().equals(context) ) {
				return registered;
			}
		}

		StoreContext result = this.globalContext;
		if ( forceCreation && !context.equals(this) ) {
			this.contexts.add( result = new StoreContext(context) );
		}

		return result;
	}
	
	@Override
	public <T extends IStore<?>, V extends IModelData> T findByType(
			Class<? extends V> type) {
		T result = null;
		for ( StoreContext context : this.contexts ) {
			result = this.<T, V>findByTypeWithinContext(type, context);
			if ( result != null ) {
				break;
			}
		}
		
		if ( result != null ) {
			return result;
		} else {
			return this.<T, V>findByTypeWithinContext(type, this.globalContext );
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T extends IStore<?>, V extends IModelData> T findByTypeWithinContext(
			Class<? extends V> type, StoreContext context ) {
		for ( IStore<?> store : context.list() ) {
			if ( store.getType().isInstance( type ) ) {
				return (T) store;
			}
		}
		
		return null;
	}

	@Override
	public <T extends IStore<?>> T getStore( Class<? extends T> clazz ) {
		return this.getStore( this, clazz );
	}

	@SuppressWarnings("unchecked")
	public <T extends IStore<?>> T getStore( Object context, Class<? extends T> clazz ) {
		if ( clazz == null ) {
			throw new IllegalArgumentException("null");
		}

		StoreContext storeContext = this.getContext(context, true);

		T store = storeContext.getItem(clazz);
		if ( store != null ) {
			return store;
		}

        store = (T) this.createStoreInstance(clazz);
        if ( store != null ) {
		    storeContext.addItem( store );
        }

		return store;
	}

    abstract protected <T extends IStore<?>> T createStoreInstance( Class<? extends T> clazz );

	public <T extends IStore<?>> void register( T store ) {
		this.register( this, store);
	}

	@Override
	public <T extends IStore<?>> void register( Object context, T store ) {
		this.getContext( context, true ).addItem( store );
	}

	@Override
	public Collection<IStore<?>> list() {
		Collection<IStore<?>> stores = new HashSet<IStore<?>>();
		for ( StoreContext context : this.contexts ) {
			stores.addAll( context.list() );
		}

		return stores;
	}

	@Override
	public Collection<IStore<?>> list( Object context ) {
		if ( !this.isRegistered(context) ) {
			return new HashSet<IStore<?>>();
		}

		return this.getContext(context).list();
	}

	protected class StoreContext {
		private Object context;
		private Map<Class<? extends IStore<?>>, IStore<?>> items =
					new HashMap<Class<? extends IStore<?>>, IStore<?>>();

		public StoreContext( Object context ) {
			this.context = context;
		}

		public Object getContext() {
			return this.context;
		}

		@SuppressWarnings("unchecked")
		public <T extends IStore<?>> T getItem( Class<? extends T> clazz ) {
			return (T) this.items.get(clazz);
		}

		@SuppressWarnings("unchecked")
		public void addItem( IStore<?> store ) {
			this.items.put( (Class<? extends IStore<?>>) store.getClass(), store );
		}

		public Collection<IStore<?>> list() {
			Collection<IStore<?>> stores = new HashSet<IStore<?>>();
			stores.addAll( this.items.values() );
			return stores;
		}

	}
	
}
