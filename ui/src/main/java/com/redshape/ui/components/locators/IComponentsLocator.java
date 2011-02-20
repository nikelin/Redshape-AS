package com.redshape.ui.components.locators;

import java.util.Collection;

import com.redshape.ui.components.IComponent;

public interface IComponentsLocator {
	
	public Collection<IComponent> locate() throws LocationException;
	
}
