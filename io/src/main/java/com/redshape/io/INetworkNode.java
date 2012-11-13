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

/**
 * @author nikelin
 */
public interface INetworkNode {

    public InetAddress getNetworkPoint();

    public void setNetworkPoint(InetAddress point);

    public void addPort(NetworkNodePort port);

    public Collection<NetworkNodePort> getPorts();

    public boolean hasPort(Integer port);

    public void setOS(NetworkNodeOS os);

    public NetworkNodeOS getOS();

    public PlatformType getPlatformType();

    public void setPlatformType( PlatformType type );

}
