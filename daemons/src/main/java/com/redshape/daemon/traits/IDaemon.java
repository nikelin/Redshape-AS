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

import com.redshape.daemon.DaemonAttribute;
import com.redshape.daemon.DaemonException;
import com.redshape.daemon.DaemonState;
import com.redshape.utils.events.IEventDispatcher;

public interface IDaemon<T> extends IEventDispatcher {

    /**
     * Starts
     */
    public void start() throws DaemonException;

    /**
     * Stops
     */
    public void stop() throws DaemonException;

    /**
     * Get com.redshape.daemon-specific attribute
     * 
     * @param name
     * @param <V>
     * @return
     */
    public <V> V getAttribute(T name);

    /**
     * Provide ability to specify some of com.redshape.daemon attributes
     * 
     * @param name
     * @param value
     */
    public void setAttribute(T name, Object value);

    /**
     * Get com.redshape.daemon attributes
     *
     * @return
     */
    public DaemonAttribute[]  getAttributes();

    /**
     * Returns current com.redshape.daemon state
     * @return
     */
    public DaemonState getState();

    /**
     * Change com.redshape.daemon state
     */
    public void changeState(DaemonState state);

}
