package com.redshape.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventType;

public class InteractionAction extends AbstractAction {
	private AppEvent event;
	
	public InteractionAction( String name, EventType type ) {
		this( name, new AppEvent( type ) );
	}
	
	public InteractionAction( String name, AppEvent event ) {
		super(name);
		
		this.event = event;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		Dispatcher.get().forwardEvent( this.event );
	}
	
}
