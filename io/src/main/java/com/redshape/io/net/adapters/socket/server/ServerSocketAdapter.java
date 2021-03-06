/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.io.net.adapters.socket.server;

import com.redshape.io.net.adapters.socket.SocketAdapterFactory;
import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.net.adapters.socket.events.SocketAdapterEvent;
import com.redshape.utils.EventObject;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.rmi.ServerException;
import java.util.Date;
import java.util.Observable;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 4, 2010
 * Time: 7:21:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerSocketAdapter extends Observable implements IServerSocketAdapter {
    private ServerSocket socket;
    private String host;
    private Integer port;

    public ServerSocketAdapter( String host, Integer port ) throws IOException, ServerException {
        this.host = host;
        this.port = port;
        this.init();
    }

    public ServerSocketAdapter( ServerSocket socket ) {
        this.socket = socket;
    }

    @Override
    public void setHost( String host ) {
        this.host = host;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void setPort( int port ) {
        this.port = port;
    }

    @Override
    public Integer getPort() {
        return this.port;
    }

    public ServerSocket getSocket() {
        return this.socket;
    }

    protected void setSocket( ServerSocket socket ) {
        this.socket = socket;
    }

    @Override
    public void close() throws IOException {
        this.notifyObservers( new EventObject( SocketAdapterEvent.CLOSED ) );
        this.socket.close();
    }

    @Override
    public ServerSocketChannel getChannel() {
        return this.socket.getChannel();
    }

    @Override
    public InetAddress getInetAddress() {
        return this.socket.getInetAddress();
    }

    @Override
    public ISocketAdapter accept() throws IOException {
        Socket socket = this.socket.accept();

        this.notifyObservers( new EventObject( SocketAdapterEvent.ACCEPTED, socket ) );

        ISocketAdapter socketAdapter = SocketAdapterFactory.getDefault().createSocketAdapter( socket );
        socketAdapter.setCreated( new Date() );

        return socketAdapter;
    }

    @Override
    public void startListening() throws IOException {
        this.socket.bind( new InetSocketAddress( this.getHost(), this.getPort() ) );
    }

    protected void init() throws IOException, ServerException {
        this.notifyObservers( new EventObject( SocketAdapterEvent.INIT ) );
        
        this.socket = this.createSocketInstance();
    }

    protected ServerSocket createSocketInstance() throws IOException {
        return ServerSocketFactory.getDefault().createServerSocket();
    }
}
