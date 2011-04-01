package com.redshape.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventType;
import com.redshape.ui.events.IEventHandler;

public class InteractionAction extends AbstractAction {
	private static final long serialVersionUID = 323352518927504082L;
	
	private AppEvent event;
	private IEventHandler handler;
	
	public InteractionAction( String name, IEventHandler handler ) {
		this(name, (EventType) null );
		
		this.handler = handler;
	}
	
	public InteractionAction( String name, EventType type ) {
		this( name, new AppEvent( type ) );
	}
	
	public InteractionAction( String name, AppEvent event ) {
		super(name);
		
		this.event = event;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		if ( this.handler == null ) {
			Dispatcher.get().forwardEvent( this.event );
		} else {
			this.handler.handle( new AppEvent() );
		}
	}
	
}
