package com.redshape.ui.components;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Action;

import com.redshape.ui.events.EventDispatcher;
import com.redshape.ui.utils.UIConstants;

public abstract class AbstractComponent extends EventDispatcher implements IComponent {
	private String name;
	private String title;
	private boolean doRenderMenu;
	private List<Action> actions = new ArrayList<Action>();
	
	public AbstractComponent( String name, String title ) {
		this(name, title, true);
	}
	
	public AbstractComponent( String name, String title, boolean doRenderMenu ) {
		super();
		
		this.doRenderMenu(doRenderMenu);
		
		this.name = name;
		this.title = title;
	}
	
	@Override
	public void render( Container container ) {
		
	}
	
	@Override
	public boolean doRenderMenu() {
		return this.doRenderMenu;
	}
	
	@Override
	public void doRenderMenu( boolean value ) {
		this.doRenderMenu = value;
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
	public void setName( String name ) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void addAction( Action action ) {
		this.actions.add(action);
	}
	
	public void setActions( List<Action> actions ) {
		this.actions = actions;
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
