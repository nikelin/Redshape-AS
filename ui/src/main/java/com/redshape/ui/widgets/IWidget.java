package com.redshape.ui.widgets;

import java.awt.Container;
import com.redshape.ui.events.IEventDispatcher;

public interface IWidget extends IEventDispatcher  {

	public String getTitle();
	
	public void setTitle( String title );
	
	public String getName();
	
	public void setName( String name );
	
	public void init();
	
	public void activate();
	
	public void deactivate();
	
	public void unload( Container component );
	
	public void render( Container component );
	
}
