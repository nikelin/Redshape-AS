package com.redshape.io.protocols.vanilla;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.dispatchers.IVanillaDispatcher;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.vanilla.response.IApiResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 12:09:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IVanillaProtocol<
						E extends IRequester,
                        T extends IApiRequest,
                        D extends IVanillaDispatcher<E, T, V>,
                        V extends IApiResponse>
        extends IProtocol<E, IApiRequest, T, IVanillaDispatcher<E, T, V>, D, V> {
}
