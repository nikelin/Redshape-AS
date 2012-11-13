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

package com.redshape.ui.windows;

import com.redshape.utils.IFilter;

import java.util.Collection;

public interface IWindowsManager<V> {
	
	/**
	 * Create or return exists window instance
	 * 
	 * @param clazz
	 * @return
	 */
	public <T extends V> T get( Class<T> clazz );
	
	/**
	 * Create new window if given window class does not exists
	 * in registry, elsewhere re-activate instance from registry
	 * and bring it to front
	 * 
	 * @param clazz
	 * @return
	 */
	public <T extends V> T open( Class<T> clazz );
	
	/**
	 * Make exists window visible
	 * 
	 * @param window
	 */
	public void open( V window );
	
	/**
	 * Make window closed
	 * @param window
	 */
	public void close( V window );

    /**
     * Close all windows which is ancestors of a given class
     * @param windowClazz
     */
    public void close( Class<? extends V> windowClazz );
	
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

    public Collection<V> list();

    public V getFocusedWindow();

}
