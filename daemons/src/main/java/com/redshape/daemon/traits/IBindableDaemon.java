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

package com.redshape.daemon.traits;

import com.redshape.daemon.IDaemonAttributes;

/**
 * Interface for daemons which have ability to be located by remote
 * clients based on theirs host and port, with optional 'path' part.
 * 
 * @author nikelin
 *
 * @TODO: add setters
 * @param <T>
 */
public interface IBindableDaemon<T extends IDaemonAttributes> 
					extends IDaemon<T> {
	/**
	 * Return host for connection string
	 * @return
	 */
	public String getHost();

	public void setHost(String host);
	
	/**
	 * Return port for connection string
	 * @return
	 */
	public Integer getPort();

	public void setPort(Integer port);
	
	/**
	 * Return path for connection string
	 * @return
	 */
	public String getPath();

	public void setPath(String path);
	
	/**
	 * Return maximal parallel client connections to current com.redshape.daemon thread
	 * @return
	 */
	public Integer getMaxConnections();

	public void setMaxConnections(Integer connections);

	public Integer getMaxAttempts();

}
