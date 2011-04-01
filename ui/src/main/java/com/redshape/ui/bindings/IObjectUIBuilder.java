package com.redshape.ui.bindings;

public interface IObjectUIBuilder {

	public <T> IObjectUI<T> createUI( Class<T> type );
	
	public <T> IObjectUI<T> createUI( Class<T> type, boolean forceCreation );
}
