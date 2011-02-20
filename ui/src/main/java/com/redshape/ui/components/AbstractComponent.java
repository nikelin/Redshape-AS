package com.redshape.ui.components;

import com.redshape.ui.events.EventDispatcher;

public abstract class AbstractComponent extends EventDispatcher implements IComponent {
	private String name;
	
	public AbstractComponent( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
