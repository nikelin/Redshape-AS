/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.servlet;

import com.redshape.applications.SpringApplication;
import com.redshape.servlet.core.controllers.FrontController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
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

    @Autowired( required = true )
    private FrontController front;

    private ServletConfig servletConfig;

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException {
        try {
            this.servletConfig = config;

        	String configPath = config.getInitParameter( CONTEXT_CONFIG_PARAMETER );
            if ( configPath == null || configPath.isEmpty() ) {
        		throw new ServletException("Spring context location must be sepecified as <init-param>!");
        	}

			System.setProperty(SpringApplication.SPRING_CONTEXT_PARAM,
					"/" + config.getServletContext().getRealPath("/") + configPath );

            if ( WebApplication.getContext() == null ) {
                this.application = new WebApplication();
                log.info( config.getInitParameterNames() );
                log.info( "Path to config: " + config.getInitParameter("resources-path") );
                this.application.setEnvArg("dataPath", config.getInitParameter("resources-path") );
                this.application.start();
            } else {
                log.info("Context already started...");
            }

            this.application.getContext().getAutowireCapableBeanFactory().autowireBean(this);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServletException( e.getMessage(), e );
        }
    }

    @Override
    public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException {
        try {
            if ( this.front == null ) {
                this.init( getServletConfig() );
            }

        	this.front.dispatch( this, request, response );
        } catch ( Throwable e ) {
        	log.error( e.getMessage(), e );
            throw new ServletException( e.getMessage(), e );
        }
    }

}
