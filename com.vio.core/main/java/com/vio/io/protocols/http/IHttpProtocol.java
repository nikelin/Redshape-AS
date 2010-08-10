package com.vio.io.protocols.http;

import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.http.request.IHttpRequest;
import com.vio.io.protocols.http.response.IHttpResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 11:11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpProtocol<T extends IHttpRequest, V extends IHttpResponse, I extends BufferedInput> extends IProtocol<T, V, I> {
}
