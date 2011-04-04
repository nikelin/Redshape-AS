package com.redshape.ui.bindings.render;

import com.redshape.ui.UIException;
import com.redshape.ui.bindings.render.components.ObjectUI;

public interface IViewRenderer<T> {

	public ObjectUI render( T parent, Class<?> object ) throws UIException;
	
}
