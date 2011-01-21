package com.redshape.io.net.adapters.socket.client;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 1:52:57 PM
 * To change this template use File | Settings | File Templates.
 */

public interface ISocketAdapter {

    public Date getCreated();

    public void setCreated( Date date );

    public java.net.InetAddress getInetAddress();
    
    public java.net.InetAddress getLocalAddress();
    
    public int getPort();
    
    public int getLocalPort();
    
    public java.net.SocketAddress getRemoteSocketAddress();
    
    public java.net.SocketAddress getLocalSocketAddress();
    
    public java.nio.channels.SocketChannel getChannel();
    
    public java.io.InputStream getInputStream() throws java.io.IOException;
    
    public java.io.OutputStream getOutputStream() throws java.io.IOException;
    
    public boolean getTcpNoDelay() throws java.net.SocketException;
    
    public int getSoLinger() throws java.net.SocketException;
    
    public boolean getOOBInline() throws java.net.SocketException;
    
    public void setSoTimeout(int i) throws java.net.SocketException;
    
    public int getSoTimeout() throws java.net.SocketException;
    
    public void setSendBufferSize(int i) throws java.net.SocketException;
    
    public int getSendBufferSize() throws java.net.SocketException;
    
    public void setReceiveBufferSize(int i) throws java.net.SocketException;
    
    public int getReceiveBufferSize() throws java.net.SocketException;
    
    public void setKeepAlive(boolean b) throws java.net.SocketException;
    
    public boolean getKeepAlive() throws java.net.SocketException;

    public int getTrafficClass() throws java.net.SocketException;
    
    public boolean getReuseAddress() throws java.net.SocketException;
    
    public void close() throws java.io.IOException;
    
    public void shutdownInput() throws java.io.IOException;
    
    public void shutdownOutput() throws java.io.IOException;
    
    public boolean isConnected();
    
    public boolean isBound();
    
    public boolean isClosed();
    
    public boolean isInputShutdown();
    
    public boolean isOutputShutdown();
    
    
}
