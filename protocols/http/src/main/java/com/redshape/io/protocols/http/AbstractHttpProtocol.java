package com.redshape.io.protocols.http;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.protocols.core.AbstractProtocol;
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
						E extends IRequester,
                        T extends IHttpRequest,
                        D extends IHttpDispatcher<E, T, R>,
                        R extends IHttpResponse>
        extends AbstractProtocol<E, IHttpRequest, T, IHttpDispatcher<E, T, R>, D, R>
        implements IHttpProtocol<E, T, D, R> {


	
	
    public AbstractHttpProtocol( Class<? extends IHttpProtocol<E, T, D, R>> protocol ) {
        this.initializeDispatchers();
    }

    // @FIXME: important!
    protected void initializeDispatchers() {
    	throw new RuntimeException("Not implemented yet");
    }

}
