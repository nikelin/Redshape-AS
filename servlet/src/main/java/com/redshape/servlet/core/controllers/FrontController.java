package com.redshape.servlet.core.controllers;

import com.redshape.servlet.actions.exceptions.AbstractPageException;
import com.redshape.servlet.core.controllers.plugins.IPlugin;
import com.redshape.servlet.core.HttpRequest;
import com.redshape.servlet.core.HttpResponse;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.dispatchers.DispatchException;
import com.redshape.servlet.dispatchers.http.IHttpDispatcher;
import com.redshape.servlet.routes.IHttpRouter;
import com.redshape.servlet.views.ILayout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrontController {
    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( FrontController.class );

    @Autowired( required = true )
    private ILayout layout;
    
    private IHttpDispatcher dispatcher;

    private IHttpRouter router;
    
    private Set<IPlugin> plugins = new HashSet<IPlugin>();

    private enum DispatchingStage {
        PRE_DISPATCH,
        POST_DISPATCH
    };
    
    public FrontController( IHttpRouter router ) {
    	this.router = router;
    }
  
    public void setLayout( ILayout layout ) {
        this.layout = layout;
    }

    public ILayout getLayout() {
        return this.layout;
    }

    public void setDispatcher( IHttpDispatcher dispatcher ) {
        this.dispatcher = dispatcher;
    }

    public IHttpDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public IHttpRouter getRouter() {
        return this.router;
    }
    
    public void setRouter( IHttpRouter router ) {
        this.router = router;
    }

    public void addPlugin( IPlugin plugin ) {
        this.plugins.add( plugin );
    }

    public void setPlugins( Set<IPlugin> plugins ) {
    	this.plugins = plugins;
    }
    
    public Set<IPlugin> getPlugins() {
        return this.plugins;
    }

    public void dispatch( HttpServlet servlet, HttpServletRequest request, HttpServletResponse response )
                                                throws DispatchException, AbstractPageException {
        HttpRequest routedRequest = this.getRouter().route(request);
        HttpResponse wrappedResponse = new HttpResponse( response );

        this._invokePlugins( DispatchingStage.PRE_DISPATCH, routedRequest, wrappedResponse );

        this.getDispatcher().dispatch( routedRequest, wrappedResponse );

        this._invokePlugins( DispatchingStage.POST_DISPATCH, routedRequest, wrappedResponse );
    }

    private void _invokePlugins( DispatchingStage stage, IHttpRequest request, IHttpResponse response ) {
        for ( IPlugin plugin : this.plugins ) {
            switch ( stage ) {
                case PRE_DISPATCH:
                    plugin.preDispatch( request, response );
                break;
                case POST_DISPATCH:
                    plugin.postDispatch( request, response );
                break;
            }
        }
    }

}
