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

package com.redshape.utils.events;

public abstract class AbstractEvent implements IEvent {

	private static final long serialVersionUID = -8828874673088349336L;
	
	private Object[] arguments;
	
	public AbstractEvent() {
		this( new Object[] {} );
	}
	
	public AbstractEvent( Object... args ) {
		this.arguments = args;
	}
	
    @SuppressWarnings("unchecked")
	public <V> V[] getArgs() {
    	return (V[]) this.arguments;
    }

    public void setArgs( Object... args ) {
    	this.arguments = args;
    }

    @SuppressWarnings("unchecked")
	public <V> V getArg( int index ) {
    	if ( index >= this.arguments.length || index < 0 ) {
    		throw new IllegalArgumentException("Wrong argument index");
    	}
    	
    	return (V) this.arguments[index];
    }
	
}
