package com.redshape.ui.data.bindings.properties;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.data.bindings.render.IViewRenderer;

public interface IPropertyUIBuilder {
	
	public <T> IPropertyUI<T> createListRenderer( IViewRenderer<?> renderingContext, IBindable descriptor )
		throws InstantiationException;
	
	public <T> IPropertyUI<T> createRenderer( IBindable type )
		throws InstantiationException;
	
	public <T> void registerRenderer( Class<T> type, Class<? extends IPropertyUI<T>> renderer );
	
}
