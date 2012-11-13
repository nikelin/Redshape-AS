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

package com.redshape.servlet.core.servlet;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 12, 2010
 * Time: 8:21:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourcesInitializerServlet extends HttpServlet {
	private static final long serialVersionUID = 6376019561544012714L;
	
	private final static Logger log = Logger.getLogger( ResourcesInitializerServlet.class );
    private final static String CONFIG_PARAMETER = "classpath";

    @Override
    public void init() {
        String config = this.getServletConfig().getInitParameter( CONFIG_PARAMETER );

        StringTokenizer tokenizer = new StringTokenizer(config, "\n");
        while ( tokenizer.hasMoreTokens() ) {
            String path = getServletContext().getRealPath("/") + tokenizer.nextToken().trim();
            log.info( "Servlet path: " + path );

            System.setProperty("java.class.path", System.getProperty("java.class.path") + File.pathSeparator + path );
        }

    }

}
