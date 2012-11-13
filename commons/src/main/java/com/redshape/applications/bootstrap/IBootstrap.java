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

package com.redshape.applications.bootstrap;

import com.redshape.utils.TimeSpan;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 1:56:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBootstrap {

    public void scheduleTask( Runnable runnable, TimeSpan delay, TimeSpan interval );

    public void addAction( IBootstrapAction action );

    public void clearActions();

    public void clear();
 
    public void removeAction( Object id );

    public void enableAction( Object id );

    public void disableAction( Object id );

    public List<IBootstrapAction> getActions();

    public IBootstrapAction getAction( Object id );

    public void init() throws BootstrapException;

}
