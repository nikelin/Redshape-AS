package com.redshape.io.protocols.http;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.dispatchers.IHttpDispatcher;
import com.redshape.io.protocols.http.request.IHttpRequest;
import com.redshape.io.protocols.http.response.IHttpResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 11:11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpProtocol<
					E extends IRequester,
                    T extends IHttpRequest,
                    D extends IHttpDispatcher<E, T, V>,
                    V extends IHttpResponse>
        extends IProtocol<E, IHttpRequest, T, IHttpDispatcher<E, T, V>, D, V> {
}
