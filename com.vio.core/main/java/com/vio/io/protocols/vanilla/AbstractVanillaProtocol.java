package com.vio.io.protocols.vanilla;

import com.vio.api.dispatchers.vanilla.IVanillaDispatcher;
import com.vio.api.dispatchers.vanilla.InterfaceInvocationsDispatcher;
import com.vio.api.dispatchers.vanilla.MethodInvocationsDispatcher;
import com.vio.io.protocols.core.AbstractProtocol;
import com.vio.io.protocols.core.request.RequestType;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.vanilla.request.IApiRequest;
import com.vio.io.protocols.vanilla.response.IApiResponse;

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
                        extends AbstractProtocol<T, D, R, I>
                        implements IVanillaProtocol<T, D, R, I> {

    public AbstractVanillaProtocol( Class<? extends IVanillaProtocol> self ) {
        this.initializeDispatchers();
    }

    protected void initializeDispatchers() {
        this.setRequestsDispatcher( RequestType.INTERFACE_INVOKE, (D) new InterfaceInvocationsDispatcher() );
        this.setRequestsDispatcher( RequestType.METHOD_INVOKE, (D) new MethodInvocationsDispatcher() );
    }

}
