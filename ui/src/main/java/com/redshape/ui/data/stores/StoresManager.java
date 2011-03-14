package com.redshape.ui.data.stores;

import java.util.HashMap;
import java.util.Map;

import com.redshape.ui.data.IModelType;
import com.redshape.ui.data.IStore;

public class StoresManager implements IStoresManager {
	private Map<Class<? extends IStore<?>>, IStore<?>> stores = new HashMap<Class<? extends IStore<?>>, IStore<?>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStore<?>> T getStore( Class<? extends T> clazz ) throws InstantiationException {
		T store = (T) this.stores.get(clazz);
		if ( store != null ) {
			return store;
		}
		
		this.stores.put( clazz, store = (T) this.createStoreInstance(clazz) );
		
		return store;
	}
	
	protected IStore<?> createStoreInstance( Class<? extends IStore<?>> clazz ) throws InstantiationException {
		
		IStore<?> storeInstance;
		try {
			storeInstance = clazz.newInstance();
		} catch ( Throwable e ) {
			throw new InstantiationException( e.getMessage() );
		}
		
		return storeInstance;
	}
	
	@Override
	public Iterable<IStore<?>> list() {
		return this.stores.values();
	}
	
	
}
