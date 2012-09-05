package com.redshape.daemon.services;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class ClientsFactory implements RMIClientSocketFactory, Serializable {
	private static final long serialVersionUID = 2437093004204957760L;

	private String host;
	
	public ClientsFactory( String host ) {
		this.host = host;
	}
	
	@Override
	public Socket createSocket(String host, int port) throws IOException {
		return SocketFactory.getDefault().createSocket( this.host, port );
	}
}
