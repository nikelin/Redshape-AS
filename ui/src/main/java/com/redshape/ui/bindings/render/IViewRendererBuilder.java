package com.redshape.ui.bindings.render;

public interface IViewRendererBuilder<V extends IViewRenderer<?>> {
	
	public V createRenderer( Class<?> clazz )
			throws InstantiationException;
	
}
