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

package com.redshape.io.net.broadcast;

import com.redshape.utils.events.AbstractEvent;
import com.redshape.utils.events.AbstractEventDispatcher;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author semichevsky
 * @author nikelin
 */
public class DiscoveryService extends AbstractEventDispatcher
							  implements IDiscoveryService {
	private static final Logger log = Logger.getLogger( DiscoveryService.class );

	public static class DiscoveryServiceEvent extends AbstractEvent {
		private static final long serialVersionUID = -7526735503032346849L;

		public static class BroadcastFailed extends DiscoveryServiceEvent {
			private static final long serialVersionUID = -5328541688127968799L;

			public BroadcastFailed() {}
		}

		public static class DiscoveredEvent extends DiscoveryServiceEvent {
			private static final long serialVersionUID = -5411851386135229131L;
			private InetAddress address;
			private int port;

			public DiscoveredEvent( InetAddress address, int port ) {
				this.address = address;
				this.port = port;
			}

			public int getPort() {
				return this.port;
			}

			public InetAddress getAddress() {
				return this.address;
			}
		}

	}

	private InterfaceAddress selfAddress;
	private ExecutorService threadExecutor;
    private String hostname;
    private int port;
    private Integer associatedPort;
    private Integer timeout;
    private ServerSocket listener;
    private int discoveryInterval;

    private Collection<Integer> discoveryPorts = new HashSet<Integer>();

    private boolean discoverable;
    private boolean discoverer;

    public DiscoveryService( String hostname, int port ) {
        this.hostname = hostname;
        this.port = port;
        this.threadExecutor = Executors.newFixedThreadPool(10);

        if ( this.port != 0 ) {
        	this.discoverable(true);
        }
    }

    @Override
    public Integer getPort() {
    	return this.port;
    }

    @Override
    public String getHostname() {
    	return this.hostname;
    }

    @Override
    public void setAssociatedPort( Integer port ) {
    	this.associatedPort = port;
    }

    @Override
    public Integer getAssociatedPort() {
    	return this.associatedPort;
    }

    @Override
    public void removeDiscoveryPort( Integer port ) {
    	this.discoveryPorts.remove(port);
    }

    @Override
    public Collection<Integer> getDiscoveryPorts() {
    	return this.discoveryPorts;
    }

    @Override
    public void addDiscoveryPort( Integer port ) {
    	this.discoveryPorts.add(port);
    }

    @Override
    public int getDiscoveryInterval() {
    	return this.discoveryInterval;
    }

    @Override
    public void setDiscoveryInterval( int interval ) {
    	this.discoveryInterval = interval;
    }

    @Override
    public boolean discoverer() {
    	return this.discoverer;
    }

    @Override
    public void discoverer( boolean value ) {
    	this.discoverer = value;
    }

    @Override
    public boolean discoverable() {
    	return this.discoverable;
    }

    @Override
    public void discoverable( boolean value ) {
    	this.discoverable = value;
    }

    @Override
    public void stop() throws IOException {
    	if ( this.discoverable() && this.listener != null ) {
    		this.listener.close();
    	}
    }

    @Override
    public void start() throws IOException {
    	if ( this.discoverer ) {
			this.startBroadcastThreads();
    	}

    	if ( this.discoverable ) {
    		this.bind();
    	}
    }

    protected byte[] createDiscoveryMessage() throws IOException {
    	ByteBuffer buff = ByteBuffer.allocate(64);
    	buff.put( this.getSelfAddress().getAddress().getAddress() );
    	buff.putInt( this.getAssociatedPort() );

    	return buff.array();
    }

    private void startBroadcastThreads() {
    	for ( final Integer discoveryPort : this.getDiscoveryPorts() ) {
    		log.info("Starting discovery thread on: " + discoveryPort );
	    	this.threadExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						DiscoveryService.this.broadcast(
							discoveryPort, DiscoveryService.this.createDiscoveryMessage() );
					} catch ( IOException e  ) {
						DiscoveryService.this.raiseEvent( new DiscoveryServiceEvent.BroadcastFailed() );
					}
				}
			});
    	}
    }

    protected void bind() throws IOException {
    	SocketAddress address = new InetSocketAddress( this.port );

    	DatagramChannel channel = SelectorProvider.provider().openDatagramChannel();

    	DatagramSocket socket = channel.socket();
    	socket.bind(address);
    	socket.setBroadcast(false);
    	socket.setReceiveBufferSize( 64 );

    	while( this.discoverable() ) {
    		DatagramPacket pkt = new DatagramPacket( new byte[64], 64);
    		socket.receive( pkt );

    		log.info("Rdeceived datagram packet...");
    		log.info( pkt );

    		InetAddress clientAddress = InetAddress.getByAddress(
				Arrays.copyOfRange( pkt.getData(), 0, 4 )
			);

    		Integer clientPort = ByteBuffer.wrap( Arrays.copyOfRange(
    				pkt.getData(), 4, 20 ) ).getInt();

    		this.raiseEvent( new DiscoveryServiceEvent.DiscoveredEvent( clientAddress, clientPort ) );

    		this.broadcast( clientPort, this.createDiscoveryMessage() );
    	}

    	socket.close();
    }

    protected synchronized InterfaceAddress getSelfAddress() throws IOException {
    	InterfaceAddress addr = this.selfAddress;
    	if ( null != addr ) {
    		return addr;
    	}

    	for ( InterfaceAddress interfaceAddr : NetworkInterface.getByInetAddress( InetAddress.getByName( hostname ) ).getInterfaceAddresses() ) {
    		String hostAddress = interfaceAddr.getAddress().getHostAddress();
            if ( hostAddress.equals( hostname ) ) {
            	addr = interfaceAddr;
            }
    	}

    	return this.selfAddress = addr;
    }

    protected boolean isTimedOut( Date startTime ) {
    	return null != this.getTimeout() && startTime.after( new Date( startTime.getTime() + this.getTimeout() ) );
    }

    protected void broadcast( int port, byte[] message ) throws IOException {
    	Date startTime = new Date();
    	while ( this.discoverable() && !this.isTimedOut(startTime) ) {
            InterfaceAddress addr = this.getSelfAddress();

            DatagramSocket socket =  new DatagramSocket();
            InetAddress bcastAddress = addr.getBroadcast();
            socket.connect(bcastAddress, port);

            DatagramPacket pkt = new DatagramPacket( message, message.length );
            log.info("Sending broadcast packet:" + pkt );

            socket.send( pkt );

            socket.close();
            try {
            	Thread.sleep( this.getDiscoveryInterval() );
            } catch ( InterruptedException e  ) {}
    	}
    }

	@Override
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public Integer getTimeout() {
		return this.timeout;
	}
}
