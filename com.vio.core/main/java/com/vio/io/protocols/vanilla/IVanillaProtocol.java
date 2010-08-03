package com.vio.io.protocols.vanilla;

import com.vio.io.protocols.core.IProtocol;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import com.vio.io.protocols.vanilla.response.IApiResponse;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 12:09:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IVanillaProtocol<T extends IAPIRequest, V extends IApiResponse> extends IProtocol<T, V> {
}
