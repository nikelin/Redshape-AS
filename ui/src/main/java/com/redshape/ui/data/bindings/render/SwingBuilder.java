package com.redshape.ui.data.bindings.render;

import com.redshape.ui.data.bindings.IViewModelBuilder;
import com.redshape.ui.data.bindings.properties.IPropertyUIBuilder;

public class SwingBuilder implements IViewRendererBuilder<ISwingRenderer> {
	private IViewModelBuilder modelsBuilder;
	private IPropertyUIBuilder uiBuilder;
	
	public SwingBuilder( IViewModelBuilder modelsBuilder, IPropertyUIBuilder uiBuilder ) {
		if ( modelsBuilder == null || uiBuilder == null ) {
			throw new IllegalArgumentException("null");
		}
		
		this.modelsBuilder = modelsBuilder;
		this.uiBuilder = uiBuilder;
	}
	
	@Override
	public ISwingRenderer createRenderer(Class<?> clazz)
			throws InstantiationException {
		return new ViewRenderer(this.modelsBuilder, this.uiBuilder);
	}
	
}
