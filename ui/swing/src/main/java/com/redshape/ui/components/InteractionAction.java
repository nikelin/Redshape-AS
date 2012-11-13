/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ui.components;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.UnhandledUIException;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.utils.ILambda;
import com.redshape.utils.InvocationException;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InteractionAction extends AbstractAction {
    private static final Logger log = Logger.getLogger(InteractionAction.class);
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
        try {
            if ( this.handler == null ) {
                Dispatcher.get().forwardEvent( this.event );
            } else {
                this.handler.handle( new AppEvent(null, e.getSource() ) );
            }
        } catch ( Throwable ex ) {
            log.error( ex.getMessage(), ex );
        }
	}
	
	public static <T, V> InteractionAction createAction( String name, 
					final T context, final ILambda<V> fn ) {
		return new InteractionAction(name, 
			new IEventHandler() {				
				private static final long serialVersionUID = 3043897656511284411L;

				@Override
				public void handle(AppEvent event) {
					try {
						fn.invoke( context, event );
					} catch ( InvocationException e ) {
						throw new UnhandledUIException( e.getMessage(), e );
					}
				}
			}
		);
	}
	
}
