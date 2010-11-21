package com.redshape.servlet;

import com.redshape.applications.Application;
import com.redshape.applications.ApplicationException;
import com.redshape.applications.bootstrap.Bootstrap;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 3:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebApplication extends Application {

    public WebApplication( String args[] ) throws ApplicationException {
        super( WebApplication.class, args, new Bootstrap() );
    }

}
