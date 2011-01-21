package com.redshape.io.protocols.http.routing;

import com.redshape.io.protocols.http.request.IHttpRequest;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 10:12:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRoute {
    
    public void affect( IHttpRequest route );

}
