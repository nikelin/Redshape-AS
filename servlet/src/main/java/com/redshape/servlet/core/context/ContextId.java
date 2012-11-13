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

package com.redshape.servlet.core.context;

import com.redshape.utils.IEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/2/12
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextId implements IEnum<String> {

    private String contextId;

    public static final Map<String, ContextId> REGISTRY = new HashMap<String, ContextId>();

    protected ContextId(String contextId) {
        this.contextId = contextId;
        REGISTRY.put( contextId.toLowerCase(), this );
    }

    public static final ContextId AJAX = new ContextId("ContextId.AJAX");
    public static final ContextId JSP = new ContextId("ContextId.JSP");
    public static final ContextId XSL = new ContextId("ContextId.XSL");

    public static ContextId valueOf( String value ) {
        return REGISTRY.get( value.toLowerCase() );
    }

    public static ContextId[] values() {
        return REGISTRY.values().toArray( new ContextId[ REGISTRY.size() ] );
    }

    @Override
    public String name() {
        return this.contextId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContextId contextId1 = (ContextId) o;

        if (contextId != null ? !contextId.equals(contextId1.contextId) : contextId1.contextId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return contextId != null ? contextId.hashCode() : 0;
    }

}
