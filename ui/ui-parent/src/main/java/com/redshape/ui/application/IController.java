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

package com.redshape.ui.application;

import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventHandler;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 2:50
 * To change this template use File | Settings | File Templates.
 */
public interface IController extends IEventHandler {
	
	public void registerHandler( EventType type, IEventHandler handler );
	
	public Set<? extends EventType> getRegisteredEvents();

    public void addChild( IController controller );


}
