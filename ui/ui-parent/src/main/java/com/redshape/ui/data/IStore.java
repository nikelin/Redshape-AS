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

package com.redshape.ui.data;

import com.redshape.ui.application.events.IEventDispatcher;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.utils.IFilter;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 09.01.11
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public interface IStore<V extends IModelData> extends IEventDispatcher, Serializable {

	/**
	 * Find record by applying specified filter on each record
	 * within underlying collection.
	 * The first record which will satisfy filter would be returned. 
	 * @param filter
	 * @return
	 */
	public V findRecord( IFilter<V> filter );
	
	/**
	 * Find record by applying specified filter on each record
	 * within underlying collection and return all records which satisfy filter.
	 * 
	 * @param filter
	 * @return
	 */
	public Collection<V> find( IFilter<V> filter );
	
	/**
	 * Apply filter on each record within underlying collection and return true
	 * if filter would be satisfied on all collection range and false otherwise.
	 * 
	 * @param filter
	 * @return
	 */
	public boolean filter( IFilter<V> filter );
	
	/**
	 *  Return current store records model type
	 * @return
	 */
    public IModelType getType();

	/**
	 * Return count of records withing given store
	 * @return
	 */
    public int count();

	/**
	 * Add new data record to store
	 * @param record
	 */
    public void add( V record );
    
    /**
     * Return true if any records present in the
     * collection.
     * 
     * @return
     */
    public boolean isEmpty();

	/**
	 * Remove given record from this store
	 * @param record
	 * @fires StoreEvents.Remove
	 */
    public void remove( V record );

	/**
	 * Remove record from store by its position in list
	 * @param index
	 * @files StoreEvents.Remove
	 */
    public void removeAt( int index );

	/**
	 * Return record from store by it's position in list
	 * @param index
	 * @return
	 */
    public V getAt( int index );

	/**
	 * Return store records list
	 * @return
	 */
    public Collection<V> getList();

	/**
	 * Method which delegate load invoke to related data loader
	 * and provides ability to intercept loading request.
	 * @throws LoaderException
	 * @files StoreEvent.Losad
	 */
    public void load() throws LoaderException;

	/**
	 * Removes all records from the store
	 *
	 * @files StoreEvent.Resmove
	 */
    public void clear();
    
    public void setLoader( IDataLoader<V> loader );

}
