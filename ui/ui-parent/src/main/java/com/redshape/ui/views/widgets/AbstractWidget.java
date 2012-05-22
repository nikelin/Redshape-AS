package com.redshape.ui.views.widgets;

import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventDispatcher;
import com.redshape.ui.application.events.IEventHandler;

public abstract class AbstractWidget<T> implements IWidget<T> {
	private static final long serialVersionUID = 2236681842578375780L;
	
	private IEventDispatcher dispatcher;
	private String name;
	private String title;
	
	public AbstractWidget() {
		this.dispatcher = new EventDispatcher();
	}
	
	@Override
	public void forwardEvent(AppEvent event) {
		this.dispatcher.forwardEvent(event);
	}

	@Override
	public void forwardEvent(EventType type, Object... args) {
		this.dispatcher.forwardEvent(type, args);
	}

	@Override
	public void addListener(EventType type, IEventHandler handler) {
		this.dispatcher.addListener(type, handler);
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
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

}
