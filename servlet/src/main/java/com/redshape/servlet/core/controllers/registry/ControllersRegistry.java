package com.redshape.servlet.core.controllers.registry;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.servlet.views.IViewsFactory;

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
    
    public ControllersRegistry() {
    	this(null);
    }
    
    public ControllersRegistry( Set<Class<? extends IAction>> actions ) {
    	this.actions = actions;
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
	public IAction createAction( String controller, String action ) throws InstantiationException {
        IAction actionInstance = null;
    	for ( Class<? extends IAction> actionClazz : getActions() ) {
            Action actionMeta = actionClazz.getAnnotation( Action.class );
            if ( actionMeta.controller().equals( controller )
                    && actionMeta.name().equals( action ) ) {
                actionInstance = _createInstance( actionClazz );
                break;
            }
        }

        return actionInstance;
    }

    private IAction _createInstance( Class<? extends IAction> action ) throws InstantiationException {
        try {
            IAction actionInstance = action.newInstance();
            actionInstance.setRegistry( this );
            actionInstance.setViewsFactory( this.getContext().getBean( IViewsFactory.class ) );
            
            return actionInstance;
        } catch ( InstantiationException e ) {
        	throw new InstantiationException("Unable to craete action instance");
        } catch ( IllegalAccessException e ) {
            throw new InstantiationException("Action constructor not available");
        }
    }

}
