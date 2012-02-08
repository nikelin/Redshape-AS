package com.redshape.ui.components;

import com.redshape.ui.components.locators.IComponentsLocator;
import com.redshape.ui.components.locators.LocationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComponentsRegistry implements IComponentsRegistry {
	private static final Logger log = Logger.getLogger( ComponentsRegistry.class );
	
	private List<IComponent> components = new ArrayList<IComponent>();
	private IComponentsLocator<IComponent> locator;
	
	public ComponentsRegistry( IComponentsLocator<IComponent> locator ) throws LocationException {
		this.locator = locator;
		
		this.init();
	}
	
	private void init() throws LocationException {
		if ( this.getComponentsLocator() == null ) {
			log.warn("UI components location turned off...");
			return;
		}
		
		for ( IComponent component : this.getComponentsLocator().locate() ) {
			log.info("Registering component: " + component.getName() );
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
