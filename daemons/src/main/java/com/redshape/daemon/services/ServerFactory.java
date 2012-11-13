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

package com.redshape.daemon.services;

import org.apache.log4j.Logger;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.rmi.server.RMIServerSocketFactory;

public class ServerFactory implements RMIServerSocketFactory, Serializable {
	private static final long serialVersionUID = 1430493102430287284L;

	private static final Logger log = Logger.getLogger( ServerFactory.class );

	private String host;
	private Integer maxConnections;
	
	public ServerFactory( String host, Integer maxConnections ) {
		this.host = host;
		this.maxConnections = maxConnections;
	}
	
	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		log.info("Starting repository on host: " + this.host + ":" + port );
        SocketAddress address = new InetSocketAddress( this.host, port );
		ServerSocket socket = new ServerSocket();
        socket.setReuseAddress(true);
        socket.bind( address );
        
        return socket;
	}

    public int hashCode() { return 57; }

    public boolean equals(Object o) {
        return this.getClass().equals(o.getClass());
    }
	
}
