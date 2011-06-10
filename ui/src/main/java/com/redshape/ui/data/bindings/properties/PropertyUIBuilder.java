package com.redshape.ui.data.bindings.properties;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.data.bindings.render.IViewRenderer;

public class PropertyUIBuilder implements IPropertyUIBuilder {
	private static final Logger log = Logger.getLogger( PropertyUIBuilder.class );
	private Map<Class<?>, Class<? extends IPropertyUI<?>>>
					renderers = new HashMap<Class<?>, Class<? extends IPropertyUI<?>>>();
	private Class<? extends IPropertyUI<?>> listRenderer;
	
	public void setListRenderer( Class<? extends IPropertyUI<?>> renderer ) {
		this.listRenderer = renderer;
	}
	
	@SuppressWarnings("unchecked")
	public <T> IPropertyUI<T> createListRenderer( IViewRenderer<?> renderingContext, IBindable descriptor ) throws InstantiationException {
		try {
			return (IPropertyUI<T>) this.listRenderer
											.getConstructor( IViewRenderer.class, IBindable.class )
											.newInstance( renderingContext,descriptor );
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new InstantiationException("Cannot create UI");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> IPropertyUI<T> createRenderer(IBindable descriptor) throws InstantiationException {
		try {
			Class<?> type = descriptor.getType();
			if ( type.isArray() ) {
				type = type.getComponentType();
			}
			
			for ( Class<?> renderable: this.renderers.keySet() ) {
				if ( !renderable.isAssignableFrom( type ) ) {
					continue;
				}
				
				IPropertyUI<T> ui = (IPropertyUI<T>) this.renderers.get( renderable )
																			   .getConstructor( IBindable.class )
																			   .newInstance(descriptor);
				
				return ui;
			}
			
			throw new IllegalArgumentException( "Unsupported type ( " + descriptor.getType().getCanonicalName() + " ) !" );
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
