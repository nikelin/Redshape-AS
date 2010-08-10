package com.vio.io.protocols.vanilla.hyndrators;

import com.vio.io.protocols.core.hydrators.RequestHydrator;
import com.vio.io.protocols.core.readers.ReaderException;
import com.vio.io.protocols.core.request.RequestHeader;
import com.vio.io.protocols.vanilla.request.IAPIRequest;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:28:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IApiRequestHydrator extends RequestHydrator {

    public Collection<IAPIRequest> readBody() throws ReaderException;

}
