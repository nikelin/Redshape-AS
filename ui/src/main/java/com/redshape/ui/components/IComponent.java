package com.redshape.ui.components;

import java.util.Collection;

import javax.swing.Action;

import com.redshape.ui.components.events.ComponentEvents;
import com.redshape.ui.events.IEventDispatcher;

public interface IComponent extends IEventDispatcher {
	
	public String getTitle();
	
	public String getName();
	
	public void init();
	
	public void activate();
	
	public void deactivate();
	
	public void addAction( Action action );
	
	public Collection<Action> getActions();
	
	public void removeAction( Action action );
	
}
