package com.redshape.ui;

import com.redshape.ui.application.IController;
import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.UIEvents;

import java.util.*;

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
    
    public void forwardToController( IController controller, AppEvent event ) {
    	controller.handle( event );
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
