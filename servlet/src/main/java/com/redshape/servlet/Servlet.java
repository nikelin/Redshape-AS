package com.redshape.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.redshape.servlet.actions.exceptions.AbstractPageException;
import com.redshape.servlet.actions.exceptions.PageNotFoundException;
import com.redshape.servlet.core.controllers.FrontController;
import com.redshape.servlet.dispatchers.DispatchException;

import com.redshape.utils.config.IConfig;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = -885951356937232550L;

	public static String CONTEXT_CONFIG_PARAMETER = "contextConfigPath";
	
    private static final Logger log = Logger.getLogger( Servlet.class );
    private WebApplication application;
    
    private IConfig config;
    private FrontController front;
    
    public void setFront( FrontController front ) {
    	this.front = front;
    }
    
    protected FrontController getFront() {
    	return this.front;
    }
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    protected IConfig getConfig() {
    	return this.config;
    }

    public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException {
        try {
        	List<String> args = new ArrayList<String>();
        	String configPath = config.getInitParameter( CONTEXT_CONFIG_PARAMETER );
        	if ( configPath == null || configPath.isEmpty() ) {
        		throw new ServletException("Spring context location must be sepecified as <init-param>!");
        	}
        	args.add( configPath );
        	
            this.application = new WebApplication( args.toArray( new String[ args.size() ] ) );
            log.info( config.getInitParameterNames() );
            log.info( "Path to config: " + config.getInitParameter("resources-path") );
            this.application.setEnvArg("dataPath", config.getInitParameter("resources-path") );
            this.application.start();
            
            this.setFront( this.application.getContext().getBean( FrontController.class ) );
            this.setConfig( this.application.getConfig() );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServletException( e.getMessage(), e );
        }
    }

    protected void redirect( HttpServletResponse response, String path ) {
    	try {
    		response.sendRedirect(path );
    	} catch ( IOException e ) {
    		log.error( e.getMessage(), e );
    	}
    }
    
    protected void handlePageException( AbstractPageException e, HttpServletRequest request, HttpServletResponse response ) {
    	if ( e instanceof PageNotFoundException ) {
    		this.handlePageNotFoundException( (PageNotFoundException) e, request, response );
    	}
    }
    
    protected void handlePageNotFoundException( PageNotFoundException e, HttpServletRequest request, HttpServletResponse response ) {
    	if ( !request.getRequestURI().startsWith("/errors/404") ) {
    		this.redirect(response, "/errors/404");
    	}
    }
    
    @Override
    public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException {
        try {
        	this.getFront().dispatch( this, request, response );
        } catch ( AbstractPageException e ) {
        	this.handlePageException(e, request, response);
        } catch ( DispatchException e ) {
        	log.error( e.getMessage(), e );
            throw new ServletException( e.getMessage(), e );
        }
    }

}
