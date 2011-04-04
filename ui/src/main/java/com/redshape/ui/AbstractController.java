package com.redshape.ui;

import com.redshape.ui.events.EventType;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.annotations.Action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 2:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractController implements IController {
    private static final Logger log = Logger.getLogger( AbstractController.class );

    private Set<EventType> events = new HashSet<EventType>();
    private Set<IController> childs = new HashSet<IController>();

    abstract protected void initEvents();
    
    abstract protected void initViews();
    
    protected AbstractController() {
    	this.init();
    }
    
    protected void init() {
    	this.initEvents();
    	this.initViews();
    }
    
	protected ApplicationContext getContext() {
		return UIRegistry.get( UIConstants.System.SPRING_CONTEXT );
	}
    
    protected void registerEvent( EventType type ) {
        this.events.add(type);
    }

    @Override
    public Set<EventType> getRegisteredEvents() {
        return this.events;
    }

    @Override
    public void addChild( IController controller ) {
        this.childs.add( controller );
    }

    @Override
    public void handle( AppEvent event ) {
        for ( Method method : this.getClass().getMethods() ) {
            Action annotation = method.getAnnotation( Action.class );
            if ( annotation == null ) {
                continue;
            }

            if ( annotation.eventType() != null &&
                    event.getType().getCode().equals( annotation.eventType() ) ) {
                try {
                    method.invoke( this, event );
                    break;
                } catch ( InvocationTargetException e ) {
                    throw new UnhandledUIException("View dispatching exception", e.getTargetException() );
                } catch ( Throwable e ) {
                	log.error( e.getMessage(), e );
                    break;
                }
            }
        }
    }

}
