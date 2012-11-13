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

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:17:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBootstrapAction {

    public void process() throws BootstrapException;

    public Collection<Object> getDependencies();

    public void addDependency( Object id );

    public boolean hasDependencies();

    public Object getId();

	public boolean hasMarkers( ActionMarker... markers );

	public void addMarker( ActionMarker marker );

	public boolean hasMarker( ActionMarker marker );

	public Collection<ActionMarker> getMarkers();

    public void setBootstrap( IBootstrap bootstrap );

    public IBootstrap getBootstrap();
}
