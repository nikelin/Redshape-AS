package com.vio.api.dispatchers.http;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.features.IFeatureInteractor;
import com.vio.io.protocols.http.request.IHttpRequest;
import com.vio.io.protocols.http.response.IHttpResponse;
import com.vio.io.protocols.http.routing.IHttpRouter;
import com.vio.server.ServerException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 1, 2010
 * Time: 11:33:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpDispatcher<T extends IFeatureInteractor> extends IDispatcher {

    public void dispatch( T requester, IHttpRequest request, IHttpResponse response ) throws ServerException;

    public IHttpRouter getRouter();

    public void setRouter( IHttpRouter router );

}
