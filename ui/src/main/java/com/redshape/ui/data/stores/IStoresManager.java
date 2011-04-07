package com.redshape.ui.data.stores; 

import com.redshape.ui.data.IStore;
import java.util.Collection;

public interface IStoresManager {
	
	public <T extends IStore<?>> T getStore( Class<? extends T> clazz ) throws InstantiationException;

	public <T extends IStore<?>> void register( T store );

	public Collection<IStore<?>> list();

}
