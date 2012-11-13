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

package com.redshape.ui.components.actions;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.events.components.ComponentEvents;
import com.redshape.ui.components.IComponent;

public class ComponentAction implements IEventHandler {
	private static final long serialVersionUID = 7695946569256843381L;

    private String name;
	private IComponent component;
	private IEventHandler listener;
	private EventType event;

    public static final class Empty extends ComponentAction {
		private static final long serialVersionUID = 8776697736558697728L;

		public Empty() {
            super(null, null);
        }

        public static ComponentAction create() {
            return new Empty();
        }
    }

	public ComponentAction( String name, IComponent component ) {
		this( name, component, true );
	}
	
	public ComponentAction( String name, IComponent component, boolean doContextChange ) {
		this( name, component, (EventType) null);
	}
	
	public ComponentAction( String name, IComponent component, EventType event ) {
        super();

		this.name = name;
		this.event = event;
		this.component = component;
	}
	
	public ComponentAction( String name, IComponent component, IEventHandler listener ) {
        super();

        this.name = name;
		this.listener = listener;
		this.component = component;
	}

    public String getName() {
        return name;
    }

    @Override
	public void handle(AppEvent e) {
		if ( this.listener != null ) {
			this.listener.handle( new AppEvent( ComponentEvents.ActionPerformed, e, this.component ) );
		} else {
			Dispatcher.get().forwardEvent( new AppEvent( this.event ) );
		}
	}

}
