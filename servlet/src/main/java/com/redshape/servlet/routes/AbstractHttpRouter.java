package com.redshape.servlet.routes;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHttpRouter implements IHttpRouter {
    private Set<IRoute> routes = new HashSet<IRoute>();

    @Override
    public void addRoute( IRoute route ) {
        this.routes.add( route );
    }

    public void setRoutes( Set<IRoute> routes ) {
    	this.routes = routes;
    }
    
    @Override
    public Set<IRoute> getRoutes() {
        return this.routes;
    }

}
