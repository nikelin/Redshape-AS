package com.redshape.ui.data.bindings.render;

import com.redshape.ui.application.UIException;

public interface IViewRenderer<T, V> {

	public V render( Class<?> object ) throws UIException;
	
	public V render( T parent, Class<?> object ) throws UIException;
	
}
