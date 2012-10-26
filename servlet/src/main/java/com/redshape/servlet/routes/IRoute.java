package com.redshape.servlet.routes;

import com.redshape.servlet.core.IHttpRequest;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IRoute {

    public boolean isApplicable(IHttpRequest request);

    public void apply(IHttpRequest request);

}
