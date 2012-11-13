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

import com.redshape.daemon.DaemonException;
import com.redshape.daemon.IDaemonAttributes;

import javax.jws.WebMethod;

public interface IPublishableDaemon<T, V extends IDaemonAttributes> 
								extends IDaemon<V> {

    /**
     * Returns true when someone remote calls it
     *
     * @return true
     */
    @WebMethod
    public boolean ping();

    /**
     * Returns com.redshape.daemon status when someone remote calls it
     *
     * @return
     */
    @WebMethod
    public String status();
    
    @WebMethod( exclude = true )
    public T getEndPoint();
    
    @WebMethod( exclude = true )
    public void publish() throws DaemonException;
    
    @WebMethod( exclude = true )
    public boolean doPublishing();
	
}
