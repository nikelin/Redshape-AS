package com.redshape.ui.data.stores; 

import com.redshape.ui.data.IStore;

public interface IStoresManager {
	
	public <T extends IStore<?>> T getStore( Class<? extends T> clazz ) throws InstantiationException;
	
	public Iterable<IStore<?>> list();

}
