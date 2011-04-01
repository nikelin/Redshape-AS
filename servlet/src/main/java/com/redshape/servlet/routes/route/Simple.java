package com.redshape.servlet.routes.route;

import com.redshape.servlet.core.HttpRequest;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.routes.IRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class Simple implements IRoute {
    private String inputURI;
    private String controller;
    private String action;
    private Map<String, Object> parameters = new HashMap();

    public Simple( String inputURI, String controller, String action, Map<String, Object> parameters ) {
        this.inputURI = inputURI;
        this.controller = controller;
        this.action = action;
        this.parameters = parameters;
    }

    public boolean isApplicatable( IHttpRequest request ) {
        return this.inputURI.equals( request.getRequestURI() );
    }

    public void applicate( IHttpRequest request ) {
        request.setController( this.controller );
        request.setAction( this.action );
        request.setParameters( this.parameters );
    }

}
