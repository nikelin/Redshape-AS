package com.redshape.ui.data.stores;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import java.util.Collection;

public interface IStoresManager {

	/**
	 * Return store which handle records with given type
	 * 
	 * @param type
	 * @param <T>
	 * @return
	 */
	public <T extends IStore<?>, V extends IModelData> T findByType( Class<? extends V> type );
	
	/**
	 * Return store registered within specified context
	 * @param context
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T extends IStore<?>> T getStore( Object context, Class<? extends T> clazz );

	/**
	 * Return store registered within global context
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T extends IStore<?>> T getStore( Class<? extends T> clazz );

	/**
	 * Register store within given execution context
	 * @param context
	 * @param store
	 * @param <T>
	 */
	public <T extends IStore<?>> void register( Object context, T store );

	/**
	 * Register store within global execution context
	 * @param store
	 * @param <T>
	 */
	public <T extends IStore<?>> void register( T store );

	/**
	 * List all registered stores
	 * @return
	 */
	public Collection<IStore<?>> list();

	/**
	 * List all registered stores within specified context
	 * @param context
	 * @return
	 */
	public Collection<IStore<?>> list( Object context );

}
