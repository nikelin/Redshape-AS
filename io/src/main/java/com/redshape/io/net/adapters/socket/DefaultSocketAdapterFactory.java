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
