package com.redshape.ui.components;

import com.redshape.ui.components.locators.IComponentsLocator;
import com.redshape.ui.components.locators.LocationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComponentsRegistry implements IComponentsRegistry {
	private List<IComponent> components = new ArrayList<IComponent>();
	private IComponentsLocator<IComponent> locator;
	
	public ComponentsRegistry( IComponentsLocator<IComponent> locator ) throws LocationException {
		this.locator = locator;
		
		this.init();
	}
	
	private void init() throws LocationException {
		if ( this.getComponentsLocator() == null ) {
			return;
		}
		
		for ( IComponent component : this.getComponentsLocator().locate() ) {
			this.addComponent( component );
		}
	}
	
	public boolean isRegistered( IComponent component ) {
		return this.components.contains(component);
	}
	
	public void addComponent( IComponent component ) {
		this.components.add(component);
	}
	
	public void removeComponent( IComponent component ) {
		this.components.remove(component);
	}
	
	public Collection<IComponent> getComponents() {
		return this.components;
	}
	
	protected IComponentsLocator<IComponent> getComponentsLocator() {
		return this.locator;
	}
	
	public void setComponentsLocator( IComponentsLocator<IComponent> locator ) {
		this.locator = locator;
	}
	
}
