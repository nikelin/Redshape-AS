package com.redshape.ui.application;

import com.redshape.ui.application.annotations.Action;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.Commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 2:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractController implements IController {
	private static final long serialVersionUID = -326558311353904534L;

    private Set<EventType> events = new HashSet<EventType>();
    private Map<EventType, IEventHandler> handlers = new HashMap<EventType, IEventHandler>();
	private Set<IController> childs = new HashSet<IController>();
	private IControllerInitializer initializer;
	
	protected AbstractController() {
		this(null);
	}
	
    protected AbstractController( IControllerInitializer initializer ) {
    	super();
    	
    	this.initializer = initializer;
    	
    	this.init();
    }
    
    abstract protected void initEvents();
    
    abstract protected void initViews();
    
    @Override
    public void registerHandler( EventType type, IEventHandler handler ) {
    	Commons.checkNotNull(type);
    	Commons.checkNotNull(handler);
    	
    	this.registerEvent(type);
    	
    	if ( this.handlers.containsKey(type) ) {
    		throw new IllegalArgumentException("Requested event type" +
    				" already relates with handler object");
    	}
    	
		this.handlers.put( type, handler );
    }
    
    
    protected void init() {
    	this.initEvents();
    	this.initViews();
    	
    	if ( this.initializer != null ) {
    		this.initializer.init(this);
    	}
    }
    
	protected IBeansProvider getContext() {
		return UIRegistry.getContext();
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
        IEventHandler handler = this.handlers.get( event.getType() );
        if ( handler == null ) {
        	return;
        }
        
        handler.handle(event);
    }
    

}
