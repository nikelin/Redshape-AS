package com.redshape.ui.components;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.redshape.ui.components.locators.IComponentsLocator;
import com.redshape.ui.components.locators.LocationException;

public class ComponentsRegistry implements IComponentsRegistry {
	private static final Logger log = Logger.getLogger( ComponentsRegistry.class );
	
	private Collection<IComponent> components = new HashSet<IComponent>();
	private IComponentsLocator locator;
	
	public ComponentsRegistry( IComponentsLocator locator ) throws LocationException {
		this.locator = locator;
		
		this.init();
	}
	
	private void init() throws LocationException {
		if ( this.getComponentsLocator() == null ) {
			log.warn("UI components location turned off...");
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
	
	protected IComponentsLocator getComponentsLocator() {
		return this.locator;
	}
	
	public void setComponentsLocator( IComponentsLocator locator ) {
		this.locator = locator;
	}
	
}
