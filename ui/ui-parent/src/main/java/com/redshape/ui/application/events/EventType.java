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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public class EventType {
    private static Map<String, EventType> INDEX = new HashMap<String, EventType>();

    private String code;

    protected EventType( String code ) {
        register(code, this);

        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public boolean equals( Object type ) {
        if ( type instanceof EventType ) {
            return this.getCode().equals( ( (EventType) type ).getCode() );
        } else {
            return super.equals(type);
        }
    }

    protected static void register( String code, EventType type ) {
        if ( code == null ) {
            throw new IllegalArgumentException("null");
        }

        EventType registeredType = INDEX.get(code);
        if ( registeredType == null ) {
            INDEX.put( code, type );
            return;
        }

        if ( !registeredType.getClass().equals(type.getClass()) ) {
            throw new IllegalArgumentException("Event " + code + " already registered withing another class!");
        }
    }

    public static EventType valueOf( String type ) {
        if ( type == null ) {
            throw new IllegalArgumentException("null");
        }

        EventType object = INDEX.get(type);
        if ( object == null ) {
            throw new IllegalArgumentException("Type not registered");
        }

        return object;
    }
}
