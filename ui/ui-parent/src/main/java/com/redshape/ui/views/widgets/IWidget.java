package com.redshape.ui.views.widgets;

import com.redshape.ui.application.events.IEventDispatcher;

import java.io.Serializable;

public interface IWidget<T> extends IEventDispatcher, Serializable {

	public String getTitle();
	
	public void setTitle( String title );
	
	public String getName();
	
	public void setName( String name );
	
	public void init();
	
	public void unload( T component );
	
	public void render( T component );
	
}
