package com.redshape.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractAction;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.events.ComponentEvents;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventType;
import com.redshape.ui.events.IEventHandler;

public class ComponentAction extends AbstractAction {
	private static final long serialVersionUID = 7695946569256843381L;
	private IComponent component;
	private IEventHandler listener;
	private EventType event;
	
	public ComponentAction( String name, IComponent component ) {
		this( name, component, (EventType) null);
	}
	
	public ComponentAction( String name, IComponent component, EventType event ) {
		super( name );
	
		this.event = event;
		this.component = component;
	}
	
	public ComponentAction( String name, IComponent component, IEventHandler listener ) {
		super( name );
	
		this.listener = listener;
		this.component = component;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( this.listener != null ) {
			this.listener.handle( new AppEvent( ComponentEvents.ActionPerformed, e, this.component ) );
		} else {
			Dispatcher.get().forwardEvent( this.event );
		}
	}

}
