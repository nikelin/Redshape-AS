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

import java.io.IOException;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: stas
 * Date: 2/1/11
 * Time: 5:56 PM
 */
public interface IDiscoveryService {

	public void stop() throws IOException;

	public void start() throws IOException;

	public Integer getPort();

	public String getHostname();

	public void setTimeout( Integer timeout );

	public Integer getTimeout();

	public void setDiscoveryInterval( int interval );

	public int getDiscoveryInterval();

	public Collection<Integer> getDiscoveryPorts();

	public void removeDiscoveryPort( Integer port );

	public void addDiscoveryPort( Integer port );

	public void setAssociatedPort( Integer port );

	public Integer getAssociatedPort();

	public boolean discoverer();

	public void discoverer( boolean value );

	public boolean discoverable();

	public void discoverable( boolean value );

}
