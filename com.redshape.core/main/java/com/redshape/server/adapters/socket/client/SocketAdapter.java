package com.redshape.server.adapters.socket.client;

import java.net.Socket;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 1:56:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocketAdapter implements ISocketAdapter {
    private Socket socket;
    private Date created;

    public SocketAdapter( Socket socket ) {
        this.socket = socket;
    }

    public void setCreated( Date date ) {
        this.created = date;
    }

    public Date getCreated() {
        return this.created;
    }

    protected Socket getSocket() {
        return this.socket;
    }

    public java.net.InetAddress getInetAddress() {
        return this.socket.getInetAddress();
    }

    public java.net.InetAddress getLocalAddress() {
        return this.socket.getLocalAddress();
    }

    public int getPort() {
        return this.socket.getPort();
    }

    public int getLocalPort() {
        return this.socket.getLocalPort();
    }

    public java.net.SocketAddress getRemoteSocketAddress() {
        return this.socket.getRemoteSocketAddress();
    }

    public java.net.SocketAddress getLocalSocketAddress() {
        return this.socket.getLocalSocketAddress();
    }

    public java.nio.channels.SocketChannel getChannel() {
        return this.socket.getChannel();
    }

    public java.io.InputStream getInputStream() throws java.io.IOException {
        return this.socket.getInputStream();
    }

    public java.io.OutputStream getOutputStream() throws java.io.IOException {
        return this.socket.getOutputStream();
    }

    public boolean getTcpNoDelay() throws java.net.SocketException {
        return this.socket.getTcpNoDelay();
    }

    public int getSoLinger() throws java.net.SocketException {
        return this.socket.getSoLinger();
    }

    public boolean getOOBInline() throws java.net.SocketException {
        return this.socket.getOOBInline();
    }

    public void setSoTimeout(int i) throws java.net.SocketException {
        this.socket.setSoTimeout(i);
    }

    public int getSoTimeout() throws java.net.SocketException {
        return this.socket.getSoTimeout();
    }

    public void setSendBufferSize(int i) throws java.net.SocketException {
        this.socket.setSendBufferSize(i);
    }

    public int getSendBufferSize() throws java.net.SocketException {
        return this.socket.getSendBufferSize();
    }

    public void setReceiveBufferSize(int i) throws java.net.SocketException {
        this.socket.setReceiveBufferSize(i);
    }

    public int getReceiveBufferSize() throws java.net.SocketException {
        return this.socket.getReceiveBufferSize();
    }

    public void setKeepAlive(boolean b) throws java.net.SocketException {
        this.socket.setKeepAlive(b);
    }

    public boolean getKeepAlive() throws java.net.SocketException {
        return this.socket.getKeepAlive();
    }

    public int getTrafficClass() throws java.net.SocketException {
        return this.socket.getTrafficClass();
    }

    public boolean getReuseAddress() throws java.net.SocketException {
        return this.socket.getReuseAddress();
    }

    public void close() throws java.io.IOException {
        this.socket.close();
    }

    public void shutdownInput() throws java.io.IOException {
        this.socket.shutdownInput();
    }

    public void shutdownOutput() throws java.io.IOException {
        this.socket.shutdownOutput();
    }

    public boolean isConnected() {
        return this.socket.isConnected();
    }

    public boolean isBound() {
        return this.socket.isBound();
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }

    public boolean isInputShutdown() {
        return this.socket.isInputShutdown();
    }

    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown();
    }
}
