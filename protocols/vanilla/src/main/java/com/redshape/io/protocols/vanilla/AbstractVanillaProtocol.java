package com.redshape.io.protocols.vanilla;

import com.redshape.io.protocols.core.AbstractProtocol;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.dispatchers.IVanillaDispatcher;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.IApiResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 11, 2010
 * Time: 2:41:37 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractVanillaProtocol<T extends IApiRequest,
                                              D extends IVanillaDispatcher,
                                              R extends IApiResponse>
                        extends AbstractProtocol<IApiRequest, T, IVanillaDispatcher, D, R>
                        implements IVanillaProtocol<T, D, R> {

    public AbstractVanillaProtocol( Class<? extends IVanillaProtocol> self ) {
        this.initializeDispatchers();
    }

    // @FIXME: important. due to protocols refactoring
    protected void initializeDispatchers() {
        this.setRequestsDispatcher( RequestType.INTERFACE_INVOKE, null );
        this.setRequestsDispatcher( RequestType.METHOD_INVOKE, null );
    }

}
