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

package com.redshape.ui;

import com.redshape.ui.application.IController;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.utils.UIRegistry;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:42
 * To change this template use File | Settings | File Templates.
 */
public final class Dispatcher extends EventDispatcher {
    private Set<IController> controllers = new HashSet<IController>();

    public static Dispatcher get() {
        return InstanceHolder.instance();
    }

    public void setErrorState( String message ) {
    	this.forwardEvent( new AppEvent( UIEvents.Core.Error, message ) );
    }
    
    @Override
    public void forwardEvent( AppEvent event ) {
        super.forwardEvent(event);

        for ( IController controller : this.controllers ) {
            if ( controller.getRegisteredEvents().contains(event.getType()) ) {            		
            	this.forwardToController( controller, event );
            }
        }
    }
    
    public void forwardToController( final IController controller, final AppEvent event ) {
        controller.handle(event);
    }

    public void addController( IController controller ) {
        this.controllers.add(controller);
    }

    private static class InstanceHolder {
        private static Dispatcher instance;

        public synchronized static Dispatcher instance() {
            if ( instance == null ) {
                instance = new Dispatcher();
            }

            return instance;
        }
    }

}
