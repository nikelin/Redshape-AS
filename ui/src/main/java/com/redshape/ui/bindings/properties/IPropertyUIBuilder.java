package com.redshape.ui.bindings.properties;

import com.redshape.bindings.types.IBindable;

public interface IPropertyUIBuilder {
	
	public <T> IPropertyUI<?, ?, T> createRenderer( IBindable type )
		throws InstantiationException;
	
	public <T> void registerRenderer( Class<T> type, Class<? extends IPropertyUI<?, ?, T>> renderer );
	
}
