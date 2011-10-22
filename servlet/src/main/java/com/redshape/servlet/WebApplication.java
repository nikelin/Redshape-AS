package com.redshape.servlet;

import com.redshape.applications.ApplicationException;
import com.redshape.applications.SpringApplication;

/**
 * @author nikelin
 */
public class WebApplication extends SpringApplication {

	public WebApplication() throws ApplicationException {
		this( new String[] {} );
	}

    public WebApplication( String args[] ) throws ApplicationException {
        super( args );
    }

}
