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
