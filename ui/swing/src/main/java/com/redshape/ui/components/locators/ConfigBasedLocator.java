package com.redshape.ui.components.locators;

import com.redshape.ui.components.IComponent;
import com.redshape.utils.config.IConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigBasedLocator<T extends IComponent> implements IComponentsLocator<T> {
	public static String DEFAULT_CONFIG_PATH = "ui.components";
	private IConfig config;
	private IConfig contextPart;
	private String configPath;
	
	public ConfigBasedLocator( IConfig config ) throws LocationException {
		this(config, DEFAULT_CONFIG_PATH );
	}
	
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
			this.contextPart = this.getConfig().get(this.getConfigPath());
		} catch ( Throwable e ) {
			throw new LocationException( e.getMessage(), e );
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> locate() throws LocationException {
		try {
			List<T> result = new ArrayList<T>();
			for ( IConfig componentNode : this.contextPart.childs() ) {
				result.add( this.createComponent( 
					(Class<T>) Class.forName( componentNode.get("class").value() ), 
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
	
	protected T createComponent( Class<T> clazz, IConfig componentConfig ) 
					throws LocationException {
		try {
			T component = clazz.newInstance();
			
			if ( null != componentConfig.attribute("name") ) {
				component.setName( componentConfig.attribute("name") );
			}
			
			if ( null != componentConfig.attribute("title") ) {
				component.setTitle( componentConfig.attribute("title") );
			}
			
			return component;
		} catch ( Throwable e ) {
			throw new LocationException( e.getMessage(), e );
		}
	}

}
