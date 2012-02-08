package com.redshape.ui.components;

import com.redshape.ui.application.IController;
import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.components.actions.ComponentAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractComponent<T> extends EventDispatcher implements IComponent<T> {
	private static final long serialVersionUID = -80720655173889647L;
	
	private String name;
	private String title;
	private boolean doRenderMenu;
	private IComponent parent;
	private Collection<IController> controllers = new HashSet<IController>();
	private List<IComponent> children = new ArrayList<IComponent>();
	private List<ComponentAction> actions = new ArrayList<ComponentAction>();

	public AbstractComponent() {
		this(null, null);
	}

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
	public void render( T container ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void unload( T container ) {
		throw new UnsupportedOperationException();
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
	public void addAction( ComponentAction action ) {
		this.actions.add(action);
	}
	
	public void setActions( List<ComponentAction> actions ) {
		this.actions = actions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ComponentAction> getActions() {
		return this.actions;
	}
	
	@Override
	public void removeAction( ComponentAction action ) {
		this.actions.remove(action);
	}

	@Override
	public void addController( IController controller ) {
		this.controllers.add(controller);
	}

	@Override
	public Collection<IController> getControllers() {
		return this.controllers;
	}

	@Override
	public List<IComponent> getChildren() {
		return this.children;
	}

	@Override
	public void setParent( IComponent component ) {
		this.parent = component;
	}
	
	@Override
	public IComponent getParent() {
		return this.parent;
	}
	
	@Override
	public void addChild( IComponent component ) {
		component.setParent( this );
		this.children.add(component);
	}
	
}
