package com.vio.server;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.io.protocols.core.IProtocol;
import com.vio.server.listeners.IRequestsProcessor;
import com.vio.server.listeners.connection.IConnectionListener;
import com.vio.server.listeners.request.IRequestListener;

import java.util.HashMap;
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

    public <T extends ISocketServer> T newInstance(
        Class<T> clazz, String host, Integer port,
        Boolean isSSLEnabled, Map<String, Object> properties,
        IProtocol protocol, IDispatcher dispatcher
    ) throws InstantiationException;

    public <T extends ISocketServer> T newInstance(
        Class<T> serverClass, String host, Integer port,
        Boolean isSSLEnabled, Map<String, Object> properties,
        IProtocol protocol, IDispatcher dispatcher,
        Class<? extends IRequestListener> listener
    ) throws InstantiationException;

    public <T extends ISocketServer> T newInstance(
        Class<T> serverClass, String host, Integer port,
        Boolean isSSLEnabled, Map<String, Object> properties,
        IProtocol protocol, IDispatcher dispatcher,
        Class<? extends IRequestListener> listener, IRequestsProcessor processor,
        IConnectionListener connectionsListener
    ) throws InstantiationException;

    public void setRequestsListener( Class<? extends IRequestListener> listenerClass );

    public Class<? extends IRequestListener> getRequestsListener();

    public void setRequestsProcessor( IRequestsProcessor processor );

    public IRequestsProcessor getRequestsProcessor();

    public void setProtocol( IProtocol protocol );

    public IProtocol getProtocol();

    public void setDispatcher( IDispatcher dispatcher );

    public IDispatcher getDispatcher();

    public void setConnectionsListener( IConnectionListener listener );

    public IConnectionListener getConnectionsListener();

}
