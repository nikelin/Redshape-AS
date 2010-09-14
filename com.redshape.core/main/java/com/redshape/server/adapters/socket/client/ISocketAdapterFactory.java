package com.redshape.server.adapters.socket.client;

import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 2:06:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISocketAdapterFactory {

    public ISocketAdapter createSocketAdapter( Socket socket );
}
