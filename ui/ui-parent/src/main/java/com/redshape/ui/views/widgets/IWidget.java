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

package com.redshape.ui.views.widgets;

import com.redshape.ui.application.events.IEventDispatcher;

import java.io.Serializable;

public interface IWidget<T> extends IEventDispatcher, Serializable {

	public String getTitle();
	
	public void setTitle( String title );
	
	public String getName();
	
	public void setName( String name );
	
	public void init();
	
	public void unload( T component );
	
	public void render( T component );
	
}
