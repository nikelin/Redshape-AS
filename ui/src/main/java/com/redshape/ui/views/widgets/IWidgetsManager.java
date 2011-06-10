package com.redshape.ui.views.widgets;

import java.util.Collection;
import java.util.Map;

import com.redshape.ui.utils.UIConstants;

public interface IWidgetsManager {
	
	public void registerWidget( UIConstants.Area placement, IWidget widget );
	
	public Map<UIConstants.Area, Collection<IWidget>> getWidgets();
	
	public Collection<IWidget> getWidgets( UIConstants.Area placement );
	
	public void setWidgets( Map<UIConstants.Area, Collection<IWidget>> widgets );
	
}
