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

package com.redshape.servlet.dispatchers;

import com.redshape.servlet.dispatchers.http.IHttpDispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchersFactory implements IDispatchersFactory {
    private static IDispatchersFactory defaultInstance = new DispatchersFactory();
    private Map<Class<? extends IHttpDispatcher>, IHttpDispatcher> dispatchers 
    			= new HashMap<Class<? extends IHttpDispatcher>, IHttpDispatcher>();

    public static IDispatchersFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( IDispatchersFactory instance ) {
        defaultInstance = instance;
    }

    public IHttpDispatcher getDispatcher( Class<? extends IHttpDispatcher> clazz ) throws InstantiationException {
        try {
            IHttpDispatcher dispatcher = this.dispatchers.get(clazz);
            if ( dispatcher == null ) {
                this.dispatchers.put( clazz, dispatcher = clazz.newInstance() );
            }

            return dispatcher;
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

}
