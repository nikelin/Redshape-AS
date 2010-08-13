package com.vio.io.protocols.http;

import com.vio.api.dispatchers.http.HttpDispatcher;
import com.vio.api.dispatchers.http.IHttpDispatcher;
import com.vio.io.protocols.core.AbstractProtocol;
import com.vio.io.protocols.core.request.RequestType;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.http.request.IHttpRequest;
import com.vio.io.protocols.http.response.IHttpResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 11, 2010
 * Time: 4:27:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHttpProtocol<
                        T extends IHttpRequest,
                        D extends IHttpDispatcher,
                        R extends IHttpResponse,
                        I extends BufferedInput>
        extends AbstractProtocol<T, D, R, I>
        implements IHttpProtocol<T, D, R, I> {

    public AbstractHttpProtocol( Class<? extends IHttpProtocol> protocol ) {
        this.initializeDispatchers();
    }

    protected void initializeDispatchers() {
        this.setRequestsDispatcher(RequestType.INTERFACE_INVOKE, (D) new HttpDispatcher() );
    }

}
