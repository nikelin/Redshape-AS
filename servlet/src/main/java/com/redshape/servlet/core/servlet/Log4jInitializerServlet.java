package com.redshape.servlet.core.servlet;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.http.HttpServlet;

public class Log4jInitializerServlet extends HttpServlet {
	private static final long serialVersionUID = 7305691081289436131L;

	public void init() {
	    String prefix =  getServletContext().getRealPath("/");
	    String file = getInitParameter("log4j-init-file");
	    // if the log4j-init-file is not set, then no point in trying
	    if(file != null) {
	      PropertyConfigurator.configure(prefix+file);
	    }
	}
	
}
