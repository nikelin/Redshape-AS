package com.redshape.io.protocols.dispatchers;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.protocols.http.request.IHttpRequest;
import com.redshape.io.protocols.http.response.IHttpResponse;
import com.redshape.io.protocols.http.routing.IHttpRouter;

public interface IHttpDispatcher<T extends IRequester, 
								V extends IHttpRequest, 
								Q extends IHttpResponse> 
								extends IDispatcher<T, V, Q> {
	
    public IHttpRouter getRouter();

    public void setRouter( IHttpRouter router );

}
