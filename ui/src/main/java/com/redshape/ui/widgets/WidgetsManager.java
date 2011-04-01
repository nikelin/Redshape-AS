package com.redshape.ui.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.redshape.ui.components.locators.IComponentsLocator;
import com.redshape.ui.components.locators.LocationException;
import com.redshape.ui.utils.UIConstants;

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
