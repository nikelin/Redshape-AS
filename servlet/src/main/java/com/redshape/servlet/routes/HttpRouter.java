package com.redshape.servlet.routes;

import com.redshape.servlet.core.HttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRouter extends AbstractHttpRouter {

	@Override
    public HttpRequest route( HttpServletRequest request ) {
        HttpRequest routedRequest = new HttpRequest( request );

        for ( IRoute route : this.getRoutes() ) {
            if ( route.isApplicatable( routedRequest ) ) {
                route.applicate( routedRequest );
            }
        }

        return routedRequest;
    }

}
