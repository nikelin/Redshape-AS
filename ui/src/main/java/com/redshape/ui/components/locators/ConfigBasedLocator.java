package com.redshape.ui.components.locators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.Action;

import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.actions.ComponentAction;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;
import com.redshape.utils.config.IConfig;

public class ConfigBasedLocator implements IComponentsLocator {
	private IConfig config;
	private IConfig contextPart;
	private String configPath = "ui.components";
	
	public ConfigBasedLocator( IConfig config, String configPath ) throws LocationException {
		this.config = config;
		this.configPath = configPath;
		
		this.init();
	}
	
	public void setConfig( IConfig config ) throws LocationException {
		boolean reinit = false;
		if ( config != this.config ) {
			reinit = true;
		}
		
		this.config = config;
		
		if ( reinit ) {
			this.init();
		}
	}
	
	public IConfig getConfig() {
		return this.config;
	}
	
	public void setConfigPath( String path ) throws LocationException {
		boolean reinit = false;
		if ( path != this.configPath ) {
			reinit = true;
		}
		this.configPath = path;
		
		if ( reinit ) {
			this.init();
		}
	}
	
	public String getConfigPath() {
		return this.configPath;
	}
	
	protected void init() throws LocationException {
		try {
			String[] pathParts = this.configPath.split(".");
			this.contextPart = config.get(pathParts[0]);
			int offset = 1;
			while( offset != pathParts.length ) {
				this.contextPart = this.contextPart.get( pathParts[offset++] );
			}
			
			pathParts = null;
		} catch ( Throwable e ) {
			throw new LocationException( e.getMessage(), e );
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<IComponent> locate() throws LocationException {
		try {
			Collection<IComponent> result = new HashSet<IComponent>();
			for ( IConfig componentNode : this.contextPart.childs() ) {
				result.add( this.createComponent( 
						(Class<? extends IComponent>) Class.forName( componentNode.get("class").value() ), 
						componentNode 
				) );
			}
			
			return result;
		} catch ( LocationException e ) {
			throw e;
		} catch ( Throwable e ) {
			throw new LocationException( e.getMessage(), e );
		}
	}
	
	protected IComponent createComponent( Class<? extends IComponent> clazz, IConfig componentConfig ) throws LocationException {
		try {
			IComponent component = clazz.newInstance();
			
			for ( IConfig actionNode : componentConfig.get("actions").childs() ) {
				component.addAction( this.createComponentAction( component, actionNode ) );
			}
			
			return component;
		} catch ( Throwable e ) {
			throw new LocationException( e.getMessage(), e );
		}
	}
	
	protected Action createComponentAction( IComponent component, IConfig actionConfig ) {
		return new ComponentAction( component, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
