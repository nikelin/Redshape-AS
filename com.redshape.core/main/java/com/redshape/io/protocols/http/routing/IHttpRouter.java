package com.redshape.io.protocols.http.routing;

import com.redshape.io.protocols.http.request.IHttpRequest;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 10:10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRouter {

    public void route( IHttpRequest request );

    public void addRoute( IHttpRoute route );
    
    public Collection<IHttpRoute> getRoutes();

    public void removeRoute( IHttpRoute route );

}
