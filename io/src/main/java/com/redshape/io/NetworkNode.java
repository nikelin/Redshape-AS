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

package com.redshape.io;


import java.net.InetAddress;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/2/10
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */

public class NetworkNode implements INetworkNode {
    private Collection<NetworkNodePort> ports = new HashSet();

    private NetworkNodeOS os;

    private InetAddress networkPoint;

    private PlatformType platformType;

    private Integer distance;

    private Integer latency;

    public NetworkNode() {
    	this(null);
    }
    
    public NetworkNode( InetAddress address ) {
    	super();
    	
    	this.networkPoint = address;
    }

    public void setLatency( Integer value) {
        this.latency = value;
    }

    public int getLatency() {
        return this.latency;
    }

    public void setDistance( Integer distance) {
        this.distance = distance;
    }

    public Integer getDistance() {
        return this.distance;
    }

    @Override
    public InetAddress getNetworkPoint() {
        return this.networkPoint;
    }

    @Override
    public void setNetworkPoint(InetAddress point) {
        this.networkPoint = point;
    }

    @Override
    public void addPort(NetworkNodePort port) {
        this.ports.add(port);
    }

    @Override
    public Collection<NetworkNodePort> getPorts() {
        return this.ports;
    }

    @Override
    public boolean hasPort(Integer port) {
        for ( NetworkNodePort nodePort : this.getPorts() ) {
            if ( nodePort.getPortId() == port ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setOS(NetworkNodeOS os) {
        this.os = os;
    }

    @Override
    public NetworkNodeOS getOS() {
        return this.os;
    }

    @Override
    public PlatformType getPlatformType() {
        return this.platformType;
    }

    @Override
    public void setPlatformType( PlatformType type ) {
        this.platformType = type;
    }

}
