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
