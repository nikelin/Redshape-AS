package com.redshape.io.protocols.vanilla;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.protocols.core.AbstractProtocol;
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
public abstract class AbstractVanillaProtocol<
											  E extends IRequester,
											  T extends IApiRequest,
                                              D extends IVanillaDispatcher<E, T, R>,
                                              R extends IApiResponse>
                        extends AbstractProtocol<E, IApiRequest, T, IVanillaDispatcher<E, T, R>, D, R>
                        implements IVanillaProtocol<E, T, D, R> {

    public AbstractVanillaProtocol( Class<? extends IVanillaProtocol<E, T, D, R>> self ) {
        this.initializeDispatchers();
    }

    // @FIXME: important. due to protocols refactoring
    protected void initializeDispatchers() {
    	throw new RuntimeException("Not implemented yet.");
    }

}
