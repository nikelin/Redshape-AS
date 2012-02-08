package com.redshape.ui.data.bindings.properties;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.data.bindings.render.IViewRenderer;

public interface IPropertyUIBuilder {
	
	public <T, V> IPropertyUI<T, V> createListRenderer( IViewRenderer<?, ?> renderingContext,
                                                        IBindable descriptor )
		throws InstantiationException;
	
	public <T, V> IPropertyUI<T, V> createRenderer( IBindable type )
		throws InstantiationException;
	
	public <T> void registerRenderer( Class<T> type,
                                      Class<? extends IPropertyUI<T, ?>> renderer );
	
}
