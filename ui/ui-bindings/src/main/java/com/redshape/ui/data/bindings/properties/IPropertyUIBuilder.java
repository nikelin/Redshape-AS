package com.redshape.ui.data.bindings.properties;

import com.redshape.utils.beans.bindings.types.IBindable;
import com.redshape.ui.data.bindings.render.IViewRenderer;

public interface IPropertyUIBuilder {
	
	public <T, V> IPropertyUI<T, V> createListRenderer( IViewRenderer<?, ?> renderingContext,
                                                        IBindable descriptor );
	
	public <T, V> IPropertyUI<T, V> createRenderer( IBindable type );
	
	public <T> void registerRenderer( Class<T> type,
                                      Class<? extends IPropertyUI<T, ?>> renderer );
	
}
