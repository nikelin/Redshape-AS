package com.redshape.servlet.routes;

import com.redshape.servlet.core.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRouter {

    public void addRoute( IRoute route );

    public Set<IRoute> getRoutes();

    public HttpRequest route( HttpServletRequest request );

}
