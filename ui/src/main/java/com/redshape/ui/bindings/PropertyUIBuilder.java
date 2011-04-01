package com.redshape.ui.bindings;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class PropertyUIBuilder implements IPropertyUIBuilder {
	private static final Logger log = Logger.getLogger( PropertyUIBuilder.class );
	private Map<Class<?>, Class<? extends IPropertyUI<?>>>
					renderers = new HashMap<Class<?>, Class<? extends IPropertyUI<?>>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> IPropertyUI<T> createRenderer(Class<T> type) 
			throws InstantiationException {
		try {
			 Class<? extends IPropertyUI<T>>  renderer = ( Class<? extends IPropertyUI<T>> ) this.renderers.get(type);
			if ( renderer == null ) {
				throw new InstantiationException("Renderer not found");
			}
			
			return renderer.newInstance();
		} catch ( InstantiationException e ) {
			throw e;
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new InstantiationException( e.getMessage() );
		}
	}

	@Override
	public <T> void registerRenderer(Class<T> type,
			Class<? extends IPropertyUI<T>> renderer) {
		this.renderers.put( type, renderer );
	}
	
	public void setRenderers( Map<Class<?>, Class<? extends IPropertyUI<?>>> renderers ) {
		this.renderers = renderers;
	}

}
