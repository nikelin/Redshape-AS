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

package com.redshape.ui.views.widgets;

import com.redshape.ui.components.locators.IComponentsLocator;
import com.redshape.ui.components.locators.LocationException;
import com.redshape.ui.utils.UIConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WidgetsManager implements IWidgetsManager {
	private Map<UIConstants.Area, Collection<IWidget>> widgets = new HashMap<UIConstants.Area, Collection<IWidget>>();
	private IComponentsLocator<IWidget> locator;
	
	public WidgetsManager() throws LocationException {
		this( new HashMap<UIConstants.Area, Collection<IWidget>>() );
	}
	
	public WidgetsManager( Map<UIConstants.Area, Collection<IWidget>> widgets ) throws LocationException {
		this.widgets = widgets;
		
		this.init();
	}
	
	protected void init() throws LocationException {
		if ( this.getLocator() != null ) {
//			for ( IWidget widget : this.getLocator().locate() ) {
//				this.registerWidget( widget.getPlacement(), widget );
//			}
		}
	}
	
	public IComponentsLocator<IWidget> getLocator() {
		return this.locator;
	}
	
	public void setLocator( IComponentsLocator<IWidget> locator ) {
		this.locator = locator;
	}
	
	@Override
	public void registerWidget( UIConstants.Area placement, IWidget widget) {
		if ( this.widgets.get(placement) == null ) {
			this.widgets.put( placement, new ArrayList<IWidget>() );
		}
		
		this.widgets.get(placement).add(widget);
	}

	@Override
	public Collection<IWidget> getWidgets( UIConstants.Area placement ) {
		return this.widgets.get(placement);
	}
	
	@Override
	public Map<UIConstants.Area, Collection<IWidget>> getWidgets() {
		return this.widgets;
	}

	@Override
	public void setWidgets( Map<UIConstants.Area, Collection<IWidget>> widgets) {
		this.widgets = widgets;
	}

}
