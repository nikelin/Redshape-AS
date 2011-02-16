package com.redshape.servlet;

import com.redshape.applications.IApplication;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.servlet.core.controllers.FrontController;
import com.redshape.servlet.dispatchers.DispatchException;
import com.redshape.servlet.views.Layout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
    private static final Logger log = Logger.getLogger( Servlet.class );
    private IApplication application;
    @Autowired( required = true )
    private IConfig config;
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }

    public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException {
        try {
            try {
                this.application = new WebApplication( new String[] {} );
                log.info( config.getInitParameterNames() );
                log.info( "Path to config: " + config.getInitParameter("resources-path") );
                this.application.setEnvArg("dataPath", config.getInitParameter("resources-path") );
                this.application.start();
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                log.error("Cannot init application");
            }
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServletException( e.getMessage() );
        }
    }

    @Override
    public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException {
        try {
            IConfig layoutNode = this.getConfig().get("layout");

            FrontController controller = FrontController.getInstance();

            controller.setLayout(
                new Layout(
                    layoutNode.get("basePath").value(),
                    layoutNode.get("scriptPath").value(),
                    layoutNode.get("extension").value()
                )
            );

            controller.dispatch( this, request, response );
        } catch ( DispatchException e ) {
            throw new ServletException( e.getMessage() );
        } catch ( ConfigException e ) {
            throw new ServletException("Config exception!");
        }
    }

}
