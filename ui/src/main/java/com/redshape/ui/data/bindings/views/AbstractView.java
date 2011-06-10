package com.redshape.ui.data.bindings.views;

public abstract class AbstractView<T> implements IViewModel<T> {
	private String id;
	private String title;
	private T descriptor;
	
	public AbstractView( T descriptor ) {
		this.descriptor = descriptor;
	}
	
	@Override
	public void setId( String id ) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public T getDescriptor() {
		return this.descriptor;
	}
	
	@Override
	public void setTitle( String title ) {
		this.title = title;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		return this.id;
	}

}
