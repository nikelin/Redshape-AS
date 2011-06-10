package com.redshape.ui.data.bindings.render;

import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.render.components.ObjectUI;

public interface IViewRenderer<T> {

	public ObjectUI render( Class<?> object ) throws UIException;
	
	public ObjectUI render( T parent, Class<?> object ) throws UIException;
	
}
