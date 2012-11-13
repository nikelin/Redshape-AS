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

package com.redshape.servlet.routes;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHttpRouter implements IHttpRouter {
    private Set<IRoute> routes = new HashSet<IRoute>();

    @Override
    public void addRoute( IRoute route ) {
        this.routes.add( route );
    }

    public void setRoutes( Set<IRoute> routes ) {
    	this.routes = routes;
    }
    
    @Override
    public Set<IRoute> getRoutes() {
        return this.routes;
    }

}
