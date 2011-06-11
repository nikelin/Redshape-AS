package com.redshape.servlet.dispatchers.http;

import javax.servlet.RequestDispatcher;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.redshape.servlet.actions.exceptions.AbstractPageException;
import com.redshape.servlet.actions.exceptions.PageNotFoundException;
import com.redshape.servlet.core.controllers.FrontController;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.servlet.core.controllers.registry.IControllersRegistry;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.dispatchers.DispatchException;
import com.redshape.servlet.views.ILayout;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDispatcher implements IHttpDispatcher {
	private static final Logger log = Logger.getLogger( HttpDispatcher.class );

	@Autowired
	private IControllersRegistry registry;
	
	private FrontController front;
	
	private ApplicationContext context;
	
	public void setRegistry( IControllersRegistry registry ) {
		this.registry = registry;
	}
	
	protected IControllersRegistry getRegistry() {
		return this.registry;
	}
	
	public void setFront( FrontController front ) {
		this.front = front;
	}
	
	protected FrontController getFront() {
		if ( this.front == null ) {
			this.front = this.getContext().getBean( FrontController.class );
		}
		
		return this.front;
	}
	
	protected ApplicationContext getContext() {
		return this.context;
	}
	
	@Override
	public void setApplicationContext( ApplicationContext context ) {
		this.context = context;
	}
	
	@Override
    public void dispatch( IHttpRequest request, IHttpResponse response ) 
    	throws DispatchException, AbstractPageException {
        try {
        	if ( request.getRequestURI().endsWith("jsp") ) {
        	}
        		
        	 
        	String controllerName = request.getController() == null ? "index" : request.getController();
        	String actionName = request.getAction() == null ? "index" : request.getAction();
        	
        	log.info("Requested page: " + controllerName + "/" + actionName );
        	
            IAction action = this.getRegistry().createAction( controllerName, actionName );
            if ( action == null ) {
            	throw new PageNotFoundException();
            }
            
            action.process( request, response );

            if ( action.getView().getRedirection() != null ) {
            	response.sendRedirect( action.getView().getRedirection() );
            }
            
            if ( response.isCommitted() ) {
            	return;
            }

            ILayout layout = this.getFront().getLayout();
            request.setAttribute("layout", layout );
            request.setAttribute("view", action.getView() );
 
            String actualPath = layout.getScriptPath();
            
            RequestDispatcher dispatcher = request.getRequestDispatcher( actualPath );
            
            dispatcher.forward(request, response);
        } catch ( AbstractPageException e ) {
            throw e;
        } catch ( Throwable e ) {
        	throw new DispatchException( e.getMessage(), e );
        }
    }

}
