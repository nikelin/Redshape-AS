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

package com.redshape.net;

import com.redshape.utils.IEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 14:34
 */
public class ServerAttribute implements IEnum<String>{

    private static final Map<String, ServerAttribute> REGISTRY = new HashMap<String, ServerAttribute>();
    
    private String code;

    protected ServerAttribute(String code) {
        this.code = code;
        REGISTRY.put( code, this );
    }

    public static class OS extends ServerAttribute {

        protected OS(String code) {
            super(code);
        }
        
        public static final OS Family = new OS("ServerAttribute.OS.Family");
        public static final OS Generation = new OS("ServerAttribute.OS.Generation");
        public static final OS Name = new OS("ServerAttribute.OS.Name");
    }

    public static ServerAttribute[] values() {
        return REGISTRY.values().toArray( new ServerAttribute[REGISTRY.size()] );
    }
    
    public static ServerAttribute valueOf( String code ) {
        return REGISTRY.get(code);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerAttribute)) return false;

        ServerAttribute that = (ServerAttribute) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String name() {
        return this.code;
    }
    
}
