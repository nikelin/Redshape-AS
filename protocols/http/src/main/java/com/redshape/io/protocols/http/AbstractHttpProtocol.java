package com.redshape.io.protocols.http;

import com.redshape.io.protocols.core.AbstractProtocol;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.dispatchers.IHttpDispatcher;
import com.redshape.io.protocols.http.request.IHttpRequest;
import com.redshape.io.protocols.http.response.IHttpResponse;

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
                        R extends IHttpResponse>
        extends AbstractProtocol<IHttpRequest, T, IHttpDispatcher, D, R>
        implements IHttpProtocol<T, D, R> {

	
	
    public AbstractHttpProtocol( Class<? extends IHttpProtocol> protocol ) {
        this.initializeDispatchers();
    }

    // @FIXME: important!
    protected void initializeDispatchers() {
        //@FIXME: due to `protocols` refactoring
        this.setRequestsDispatcher(RequestType.INTERFACE_INVOKE, null );
    }

}
