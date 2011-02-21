package com.redshape.ui.components;

import java.util.Collection;

import javax.swing.Action;
import com.redshape.ui.events.IEventDispatcher;

public interface IComponent extends IEventDispatcher {
	
	public boolean doRenderMenu();
	
	public void doRenderMenu( boolean value );
	
	public String getTitle();
	
	public void setTitle( String title );
	
	public String getName();
	
	public void setName( String name );
	
	public void init();
	
	public void activate();
	
	public void deactivate();
	
	public void addAction( Action action );
	
	public <T extends Action> Collection<T> getActions();
	
	public void removeAction( Action action );
	
}
