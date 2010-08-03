package com.vio.io.protocols.http.routing;

import com.vio.io.protocols.http.request.IHttpRequest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 10:14:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRouter implements IHttpRouter {
    private Set<IHttpRoute> routes = new HashSet<IHttpRoute>();

    public void addRoute( IHttpRoute route ) {
        this.routes.add( route );
    }

    public void removeRoute( IHttpRoute route ) {
        this.routes.remove( route );
    }

    public Collection<IHttpRoute> getRoutes() {
        return this.routes;
    }

    public void route( IHttpRequest request ) {
        for ( IHttpRoute route : this.getRoutes() ) {
            route.affect( request );
        }
    }

}
