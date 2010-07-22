package com.vio.io.protocols.vanilla.hyndrators;

import com.vio.io.protocols.vanilla.request.InterfaceInvocation;
import com.vio.io.protocols.hydrators.RequestHydrator;
import com.vio.io.protocols.readers.ReaderException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:28:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ApiRequestHydrator extends RequestHydrator {

    public List<InterfaceInvocation> readBody() throws ReaderException;

}
