package com.redshape.io.protocols.vanilla.hyndrators;

import com.redshape.io.protocols.core.hydrators.RequestHydrator;
import com.redshape.io.protocols.core.readers.ReaderException;
import com.redshape.io.protocols.vanilla.request.IApiRequest;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:28:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IApiRequestHydrator extends RequestHydrator {

    public Collection<IApiRequest> readBody() throws ReaderException;

}
