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

package com.redshape.ui.views;

import com.redshape.ui.utils.UIConstants;

/**
 * Views managing entity
 * 
 * @author nikelin
 */
public interface IViewsManager {
	
	/**
	 * Activate view associated with specified id
	 * @param id
	 */
	public void activate( Object id ) throws ViewException;
	
	/**
	 * Unload current active view
	 */
	public void deactivate() throws ViewException;
	
	/**
	 * Check that specified ID has associated view
	 * in the registry.
	 * @param id
	 * @return
	 */
	public boolean isRegistered( Object id );
	
	/**
	 * Add view into registry with specified identifier key
	 * @param view
	 * @param id
	 */
	public void register( IView view, Object id );
	
	/**
	 * Unload view related to specified ID and drops 
	 * all information about it from registry.
	 * 
	 * @param id
	 */
	public void unregister( Object id );
	
	/**
	 * Return ID of current active view instance
	 * @return
	 */
	public Object getActive();
	
	/**
	 * Change manager output component
	 * @param area
	 */
	public void setViewArea( UIConstants.Area area );

}
