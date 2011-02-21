package com.redshape.ui.components;

import java.util.Collection;
import java.util.HashSet;

import javax.swing.Action;

import com.redshape.ui.events.EventDispatcher;

public abstract class AbstractComponent extends EventDispatcher implements IComponent {
	private String name;
	private String title;
	private Collection<Action> actions = new HashSet<Action>();
	
	public AbstractComponent( String name, String title ) {
		this.name = name;
		this.title = title;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void addAction( Action action ) {
		this.actions.add(action);
	}
	
	public void setActions( Collection<Action> actions ) {
		this.actions
		 = actions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Action> Collection<T> getActions() {
		return (Collection<T>) this.actions;
	}
	
	@Override
	public void removeAction( Action action ) {
		this.actions.remove(action);
	}
	
}
