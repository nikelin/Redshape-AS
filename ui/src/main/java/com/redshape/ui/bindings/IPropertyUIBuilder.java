package com.redshape.ui.bindings;

public interface IPropertyUIBuilder {
	
	public <T> IPropertyUI<T> createRenderer( Class<T> type )
		throws InstantiationException;
	
	public <T> void registerRenderer( Class<T> type, Class<? extends IPropertyUI<T>> renderer );
	
}
