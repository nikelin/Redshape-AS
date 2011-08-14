package com.redshape.servlet;

import com.redshape.servlet.core.controllers.FrontController;
import com.redshape.servlet.dispatchers.DispatchException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

    private FrontController front;
    
    public void setFront( FrontController front ) {
    	this.front = front;
    }
    
    protected FrontController getFront() {
    	return this.front;
    }

    public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException {
        try {
        	List<String> args = new ArrayList<String>();
        	String configPath = config.getInitParameter( CONTEXT_CONFIG_PARAMETER );
            if ( configPath == null || configPath.isEmpty() ) {
        		throw new ServletException("Spring context location must be sepecified as <init-param>!");
        	}

        	args.add( "/" + config.getServletContext().getRealPath("/") + configPath );
        	
            this.application = new WebApplication( args.toArray( new String[ args.size() ] ) );
            log.info( config.getInitParameterNames() );
            log.info( "Path to config: " + config.getInitParameter("resources-path") );
            this.application.setEnvArg("dataPath", config.getInitParameter("resources-path") );
            this.application.start();
            
            this.setFront( this.application.getContext().getBean( FrontController.class ) );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServletException( e.getMessage(), e );
        }
    }

    @Override
    public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException {
        try {
        	this.getFront().dispatch( this, request, response );
        } catch ( DispatchException e ) {
        	log.error( e.getMessage(), e );
            throw new ServletException( e.getMessage(), e );
        }
    }

}
