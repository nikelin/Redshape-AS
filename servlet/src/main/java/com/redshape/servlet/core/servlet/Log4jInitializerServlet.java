package com.redshape.servlet.core.servlet;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.http.HttpServlet;

public class Log4jInitializerServlet extends HttpServlet {
	private static final long serialVersionUID = 7305691081289436131L;

	public void init() {
	    String prefix =  getServletContext().getRealPath("/");
	    String file = getInitParameter("log4j-init-file");
	    // if the log4j-init-file is not set, then no point in trying
	    if(file != null) {
          String path = prefix + file;
          if ( path.endsWith(".properties") ) {
	        PropertyConfigurator.configure(path);
          } else if ( path.endsWith(".xml") ) {
              DOMConfigurator.configure(path);
          } else {
              throw new IllegalArgumentException("Unknown Log4J configuration file extension");
          }
	    }
	}
	
}
