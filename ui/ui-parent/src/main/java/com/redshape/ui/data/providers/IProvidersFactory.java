package com.redshape.ui.data.providers;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.IDataLoader;

public interface IProvidersFactory {
	
	public <T extends IModelData> IStore<T> provide( Class<?> type );
	
	public <T extends IModelData> IDataLoader<T> getLoader( Object context,
                                                            Class<? extends IStore<T>> store );
	
	public <T extends IModelData> void registerLoader( Object context,
                                                       Class<? extends IStore<T>> store,
                                                       IDataLoader<T> loader );
	
	public void registerProvider( Class<?> type, Class<? extends IStore<?>> store );
	
}
