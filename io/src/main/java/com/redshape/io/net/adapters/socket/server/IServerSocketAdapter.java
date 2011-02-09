package com.redshape.io.net.adapters.socket.server;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.ServerSocketChannel;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 1:48:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IServerSocketAdapter {
    
    public void setHost( String host );

    public String getHost();

    public void setPort( int port );

    public Integer getPort();

    public void close() throws IOException;

    public ServerSocketChannel getChannel();

    public InetAddress getInetAddress();

    public ISocketAdapter accept() throws IOException;

    public void startListening() throws IOException;
}
