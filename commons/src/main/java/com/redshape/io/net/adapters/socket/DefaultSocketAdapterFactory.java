package com.redshape.io.net.adapters.socket;

import java.io.IOException;
import java.net.Socket;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.net.adapters.socket.client.SocketAdapter;
import com.redshape.io.net.adapters.socket.server.IServerSocketAdapter;
import com.redshape.io.net.adapters.socket.server.SSLServerSocketAdapter;
import com.redshape.io.net.adapters.socket.server.ServerSocketAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 7, 2010
 * Time: 11:58:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultSocketAdapterFactory extends SocketAdapterFactory {

    public IServerSocketAdapter createServerSocketAdapter( String host, int port ) throws IOException {
        return new ServerSocketAdapter( host, port );
    }

    public IServerSocketAdapter createSSLServerSocketAdapter( String host, int port ) throws IOException {
        return new SSLServerSocketAdapter( host, port );
    }

    public ISocketAdapter createSocketAdapter( Socket socket ) {
        return new SocketAdapter( socket );
    }
}
