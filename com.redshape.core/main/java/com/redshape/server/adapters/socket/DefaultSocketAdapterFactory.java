package com.redshape.server.adapters.socket;

import com.redshape.server.ServerException;
import com.redshape.server.adapters.socket.client.ISocketAdapter;
import com.redshape.server.adapters.socket.client.SocketAdapter;
import com.redshape.server.adapters.socket.server.IServerSocketAdapter;
import com.redshape.server.adapters.socket.server.SSLServerSocketAdapter;
import com.redshape.server.adapters.socket.server.ServerSocketAdapter;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 7, 2010
 * Time: 11:58:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultSocketAdapterFactory extends SocketAdapterFactory {

    public IServerSocketAdapter createServerSocketAdapter( String host, int port ) throws IOException, ServerException {
        return new ServerSocketAdapter( host, port );
    }

    public IServerSocketAdapter createSSLServerSocketAdapter( String host, int port ) throws IOException, ServerException {
        return new SSLServerSocketAdapter( host, port );
    }

    public ISocketAdapter createSocketAdapter( Socket socket ) {
        return new SocketAdapter( socket );
    }
}
