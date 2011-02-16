package com.redshape.io.protocols.http;

import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
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
                    T extends IHttpRequest,
                    D extends IHttpDispatcher,
                    V extends IHttpResponse>
        extends IProtocol<IHttpRequest, T, IHttpDispatcher, D, V> {
}
