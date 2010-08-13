package com.vio.io.protocols.vanilla;

import com.vio.api.dispatchers.vanilla.IVanillaDispatcher;
import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.core.sources.input.BufferedInput;
import com.vio.io.protocols.vanilla.request.IApiRequest;
import com.vio.io.protocols.vanilla.response.IApiResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 12:09:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IVanillaProtocol<
                        T extends IApiRequest,
                        D extends IVanillaDispatcher,
                        V extends IApiResponse,
                        I extends BufferedInput>
        extends IProtocol<T, D, V, I> {
}
