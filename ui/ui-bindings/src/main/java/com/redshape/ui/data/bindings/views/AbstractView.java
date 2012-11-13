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

package com.redshape.ui.data.bindings.views;

public abstract class AbstractView<T> implements IViewModel<T> {
	private String id;
	private String title;
	private T descriptor;
	
	public AbstractView( T descriptor ) {
		this.descriptor = descriptor;
	}
	
	@Override
	public void setId( String id ) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public T getDescriptor() {
		return this.descriptor;
	}
	
	@Override
	public void setTitle( String title ) {
		this.title = title;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		return this.id;
	}

}
