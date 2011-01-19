package com.redshape.servlet.core.controllers.plugins;

import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.IHttpRequest;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IPlugin {

    public void preDispatch( IHttpRequest request, IHttpResponse response );

    public void postDispatch( IHttpRequest request, IHttpResponse response );
}
