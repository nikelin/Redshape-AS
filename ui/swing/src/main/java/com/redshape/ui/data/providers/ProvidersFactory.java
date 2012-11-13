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

package com.redshape.ui.data.providers;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.stores.IStoresManager;

import java.util.HashMap;
import java.util.Map;

public class ProvidersFactory implements IProvidersFactory {
	private Map<Class<?>, Class<? extends IStore<?>>> providers 
					= new HashMap<Class<?>, Class<? extends IStore<?>>>();
	private Map<Object, Map<Class<? extends IStore<?>>, IDataLoader<?>>> loaders
			= new HashMap<Object, Map<Class<? extends IStore<?>>, IDataLoader<?>>>();
	private IStoresManager storesManager;
	
	public ProvidersFactory( IStoresManager storesManager ) {
		this.storesManager = storesManager;
	}
	
	public IStoresManager getStoresManager() {
		return this.storesManager;
	}
	
	public void setProviders( Map<Class<?>, Class<? extends IStore<?>>> providers ) {
		this.providers = providers;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IModelData> IStore<T> provide( Class<?> type ) {
		Class<? extends IStore<T>> storeClass = (Class<? extends IStore<T>>) this.providers.get(type);
		if ( storeClass != null ) {
			return this.getStoresManager().getStore( storeClass );
		}

		if ( IModelData.class.isAssignableFrom( type ) ) {
			return this.getStoresManager().findByType( type.asSubclass(IModelData.class) );
		}
		
		return null;
	}

	@Override
	public void registerProvider(Class<?> type, Class<? extends IStore<?>> store)  {
		this.providers.put( type, store );
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IModelData> IDataLoader<T> getLoader(Object context,
			Class<? extends IStore<T>> store) {
		if ( !this.loaders.containsKey(context) ) {
			return null;
		}
		
		return (IDataLoader<T>) this.loaders.get(context).get(store);
	}

	@Override
	public <T extends IModelData> void registerLoader(Object context,
			Class<? extends IStore<T>> store, IDataLoader<T> loader) {
		if ( this.loaders.get(context) == null ) {
			this.loaders.put( context,
				new HashMap<Class<? extends IStore<?>>, IDataLoader<?>>() );
		}
		
		this.loaders.get( context ).put( store, loader );
	}

}
