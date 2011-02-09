package com.redshape.io.protocols.dispatchers;

import com.redshape.api.dispatchers.IDispatcher;
import com.redshape.features.IFeatureInteractor;
import com.redshape.io.protocols.http.request.IHttpRequest;
import com.redshape.io.protocols.http.response.IHttpResponse;
import com.redshape.io.protocols.http.routing.IHttpRouter;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 1, 2010
 * Time: 11:33:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpDispatcher<T extends IFeatureInteractor, V extends IHttpRequest, Q extends IHttpResponse> extends IDispatcher<T, V, Q> {

    public IHttpRouter getRouter();

    public void setRouter( IHttpRouter router );

}
