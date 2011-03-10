package com.redshape.ui.windows;

import java.util.Collection;
import com.redshape.utils.IFilter;

public interface IWindowsManager<V> {
	
	/**
	 * Create new window if given window class does not exists
	 * in registry, elsewhere re-activate instance from registry.
	 * 
	 * @param <T>
	 * @param window
	 * @return
	 */
	public V open( Class<? extends V> clazz );
	
	/**
	 * Make window closed
	 * @param window
	 */
	public void close( V window );
	
	/**
	 * Delete window object instance from registry
	 * @param window
	 */
	public void destory( V window );
	
	/**
	 * Returns all opened windows in current context.
	 * 
	 * @return
	 */
	public Collection<V> getOpened();
	
	/**
	 * Returns all closed (unvisible) windows.
	 * 
	 * @return
	 */
	public Collection<V> getClosed();
	
	/**
	 * Close all opened windows
	 */
	public void closeAll();
	
	/**
	 * Filter all registered windows instances thought 
	 * given filter object and returns result.
	 * 
	 * @param filter
	 * @return
	 */
	public Collection<V> filter( IFilter<V> filter );
	
	public boolean isRegistered( V window );

}
