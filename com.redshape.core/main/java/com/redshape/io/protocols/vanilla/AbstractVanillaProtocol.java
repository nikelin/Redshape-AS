package com.redshape.io.protocols.vanilla;

import com.redshape.api.dispatchers.vanilla.IVanillaDispatcher;
import com.redshape.api.dispatchers.vanilla.InterfaceInvocationsDispatcher;
import com.redshape.api.dispatchers.vanilla.MethodInvocationsDispatcher;
import com.redshape.io.protocols.core.AbstractProtocol;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.core.sources.input.BufferedInput;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.server.ISocketServer;
import com.redshape.server.processors.connection.ClientsProcessor;
import com.redshape.server.processors.request.ApiRequestsProcessor;
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

    protected void initializeDispatchers() {
        /**
         * @TODO: to not forget fix this fucking shit
         */
        this.setClientsProcessor( new ClientsProcessor() );
        this.setRequestsProcessor( ApiRequestsProcessor.class );
        this.setRequestsDispatcher( RequestType.INTERFACE_INVOKE, new InterfaceInvocationsDispatcher() );
        this.setRequestsDispatcher( RequestType.METHOD_INVOKE, new MethodInvocationsDispatcher() );
    }

}
