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

package com.redshape.ui.application.events;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class AppEvent {
    private EventType type;
    private Object[] args;

    public AppEvent() {
    	this(null);
    }
    
    public AppEvent( EventType type ) {
        this(type, new Object[] {} );
    }

    public AppEvent( EventType type, Object... args ) {
        this.type = type;
        this.args = args;
    }

    public EventType getType() {
        return type;
    }

    public Object[] getArgs() {
        return args;
    }

    @SuppressWarnings("unchecked")
	public <V> V getArg( int num ) {
        return num >= this.args.length || num < 0 ? null : (V) this.args[num];
    }

    public boolean isSame( EventType type ) {
        return this.getType() != null ? this.getType().equals( type ) : false;
    }

    public boolean isSame( AppEvent event ) {
        return this.getType() != null ? this.getType().equals( event.getType() ) : false;
    }
}
