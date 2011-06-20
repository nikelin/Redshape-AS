package com.redshape.servlet.core.controllers.registry;

import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.utils.Commons;
import org.apache.commons.collections.FastHashMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class ControllersRegistry implements IControllersRegistry, ApplicationContextAware{
    private Set<Class<? extends IAction>> actions = new HashSet<Class<? extends IAction>>();

    private ApplicationContext context;
    private Map<String, Map<String, IAction>> registry = new FastHashMap();

    public ControllersRegistry() {
    	this(null);
    }
    
    public ControllersRegistry( Set<Class<? extends IAction>> actions ) {
    	super();
    	
    	this.actions = actions;
    }

    @Override
    public String getViewPath( IAction action ) {
        Action actionMeta = action.getClass().getAnnotation( Action.class );
        if ( actionMeta == null ) {
            return null;
        }

        String viewPath = actionMeta.view();
        if ( viewPath == null || viewPath.isEmpty() ) {
            return null;
        }

        return viewPath;
    }

    @Override
    public void setApplicationContext( ApplicationContext context ) {
    	this.context = context;
    }
    
    protected ApplicationContext getContext() {
    	return this.context;
    }
    
    @Override
	public void addAction( Class<? extends IAction> actionClazz ) {
        if ( actionClazz.getAnnotation( Action.class ) == null ) {
            throw new IllegalArgumentException("Invalid class given as action");
        }

        this.actions.add( actionClazz );
    }

    @Override
	public Set<Class<? extends IAction> > getActions() {
        return this.actions;
    }

    @Override
    public IAction getInstance( String controller, String action ) throws InstantiationException {
         if ( !this.registry.containsKey(controller) ) {
             return this.createAction( controller, action );
         }

        if ( !this.registry.get( controller ).containsKey(action) ) {
            return this.createAction( controller, action );
        }

        return this.registry.get(controller).get(action);
    }

	protected IAction createAction( String controller, String action ) throws InstantiationException {
        IAction actionInstance = null;
        IAction controllerInstance = null;
    	for ( Class<? extends IAction> actionClazz : getActions() ) {
            Action actionMeta = actionClazz.getAnnotation( Action.class );
            if ( actionMeta.controller().equals( controller )
                    && actionMeta.name().equals( action ) ) {
                actionInstance = _createInstance( actionClazz );
                break;
            } else if ( actionMeta.controller().equals( controller )
                    && actionMeta.name().equals("index") ) {
                controllerInstance = _createInstance( actionClazz );
            }
        }
    	
    	if ( actionInstance != null ) {
	    	this.getContext().getAutowireCapableBeanFactory()
	    			.autowireBean( actionInstance );

            if ( !this.registry.containsKey(controller) ) {
                this.registry.put( controller, new FastHashMap() );
            }

            this.registry.get( controller ).put( action, actionInstance );
    	}

        return Commons.select( actionInstance, controllerInstance );
    }

    private IAction _createInstance( Class<? extends IAction> action ) throws InstantiationException {
        try {
            return action.newInstance();
        } catch ( InstantiationException e ) {
        	throw new InstantiationException("Unable to craete action instance");
        } catch ( IllegalAccessException e ) {
            throw new InstantiationException("Action constructor not available");
        }
    }

}
