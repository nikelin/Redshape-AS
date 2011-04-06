package com.redshape.ui.bindings.views;

public interface IViewModel<T> {
	
	public String getId();
	
	public void setId( String id );
	
	public T getDescriptor();
	
	public String getTitle();
	
	public void setTitle( String title );
	
}
