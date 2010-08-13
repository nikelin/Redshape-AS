package com.vio.server;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.io.protocols.core.IProtocol;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 12:40:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISocketServerFactory extends IServerFactory {

    public <T extends ISocketServer> T newInstance(
        Class<T> clazz, String host,
        Integer port, Boolean isSSLEnabled, Map<String, Object> properties, IProtocol protocol
    ) throws InstantiationException;

    public void setProtocol( IProtocol protocol );

    public IProtocol getProtocol();

}
