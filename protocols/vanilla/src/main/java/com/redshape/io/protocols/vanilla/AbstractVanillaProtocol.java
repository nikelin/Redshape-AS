package com.redshape.io.protocols.vanilla;

import com.redshape.io.protocols.core.AbstractProtocol;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.dispatchers.IVanillaDispatcher;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.server.ISocketServer;
import com.redshape.server.processors.request.IRequestsProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 11, 2010
 * Time: 2:41:37 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractVanillaProtocol<T extends IApiRequest,
                                              D extends IVanillaDispatcher,
                                              R extends IApiResponse,
                                              I extends  BufferedInput>
                        extends AbstractProtocol<IApiRequest, T, IVanillaDispatcher, D, R, I>
                        implements IVanillaProtocol<T, D, R, I> {

    public AbstractVanillaProtocol( Class<? extends IVanillaProtocol> self ) {
        this.initializeDispatchers();
    }

    // @FIXME: important. due to protocols refactoring
    protected void initializeDispatchers() {
        /**
         * @TODO: to not forget fix this fucking shit
         */
        this.setClientsProcessor( null );
        this.setRequestsProcessor( null );
        this.setRequestsDispatcher( RequestType.INTERFACE_INVOKE, null );
        this.setRequestsDispatcher( RequestType.METHOD_INVOKE, null );
    }

}
