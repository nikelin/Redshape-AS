package com.redshape.ui.components.locators;

import java.util.Collection;

import com.redshape.ui.views.widgets.IWidget;

public interface IComponentsLocator<T extends IWidget> {
	
	public Collection<T> locate() throws LocationException;
	
}
