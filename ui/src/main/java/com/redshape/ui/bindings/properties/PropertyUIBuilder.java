package com.redshape.ui.bindings.properties;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.redshape.bindings.types.IBindable;

public class PropertyUIBuilder implements IPropertyUIBuilder {
	private static final Logger log = Logger.getLogger( PropertyUIBuilder.class );
	private Map<Class<?>, Class<? extends IPropertyUI<?, ?, ?>>>
					renderers = new HashMap<Class<?>, Class<? extends IPropertyUI<?, ?, ?>>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> IPropertyUI<?, ?, T> createRenderer(IBindable descriptor) throws InstantiationException {
		try {
			Class<?> type = descriptor.getType();
			if ( type.isArray() ) {
				type = type.getComponentType();
			}
			
			for ( Class<?> renderable: this.renderers.keySet() ) {
				if ( !renderable.isAssignableFrom( type ) ) {
					continue;
				}
				
				IPropertyUI<?, ?, T> ui = (IPropertyUI<?, ?, T>) this.renderers.get( renderable )
																			   .getConstructor( IBindable.class )
																			   .newInstance(descriptor);
//				if ( descriptor.getType().isArray() && !ui.isArraySupported() ) {
//					throw new InstantiationException("Registered UI does not support arrays!");
//				}
				
				return ui;
			}
			
			throw new IllegalArgumentException( "Unsupported type!" );
		} catch ( InstantiationException e ) {
			throw e;
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new InstantiationException( e.getMessage() );
		}
	}

	@Override
	public <T> void registerRenderer(Class<T> type,
			Class<? extends IPropertyUI<?, ?, T>> renderer) {
		this.renderers.put( type, renderer );
	}
	
	public void setRenderers( Map<Class<?>, Class<? extends IPropertyUI<?, ?, ?>>> renderers ) {
		this.renderers = renderers;
	}

}
