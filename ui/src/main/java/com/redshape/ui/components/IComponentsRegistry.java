package com.redshape.ui.components;

import java.util.Collection;

import com.redshape.ui.components.locators.IComponentsLocator;

public interface IComponentsRegistry {
	
	public void addComponent( IComponent component );
	
	public void removeComponent( IComponent component );
	
	public Collection<IComponent> getComponents();
	
}
