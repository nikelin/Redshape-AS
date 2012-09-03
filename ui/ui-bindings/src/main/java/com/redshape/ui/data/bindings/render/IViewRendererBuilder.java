package com.redshape.ui.data.bindings.render;

public interface IViewRendererBuilder<V extends IViewRenderer<?, ?>> {
	
	public V createRenderer( Class<?> clazz );
	
}
