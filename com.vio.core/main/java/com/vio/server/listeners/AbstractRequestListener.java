package com.vio.server.listeners;

import com.vio.server.ISocketServer;
import com.vio.server.adapters.socket.client.ISocketAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 24, 2010
 * Time: 12:49:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRequestListener<T extends ISocketServer, V extends ISocketAdapter> implements IRequestListener<T, V> {
    private V socket;
    private T context;
    public T getContext() {
        return this.context;
    }

    public void setContext( T context ) {
        this.context = context;
    }

}
