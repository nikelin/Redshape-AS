package com.redshape.ui.actions;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.UnhandledUIException;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventType;
import com.redshape.ui.events.IEventHandler;
import com.redshape.utils.IFunction;

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
	
	public static <T, V> InteractionAction createAction( String name, 
					final T context, final IFunction<T, V> fn ) {
		return new InteractionAction(name, 
			new IEventHandler() {				
				@Override
				public void handle(AppEvent event) {
					try {
						fn.invoke( context, event );
					} catch ( InvocationTargetException e ) {
						throw new UnhandledUIException( e.getMessage(), e );
					}
				}
			}
		);
	}
	
}
