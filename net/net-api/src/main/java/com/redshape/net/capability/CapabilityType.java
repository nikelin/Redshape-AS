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

package com.redshape.net.capability;

import com.redshape.utils.IEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 15:50
 */
public class CapabilityType implements IEnum<String> {
    private static final Map<String, CapabilityType> REGISTRY = new HashMap<String, CapabilityType>();
    
    private String code;

    protected CapabilityType(String code) {
        this.code = code;
        REGISTRY.put( code, this );
    }

    public static final CapabilityType FileSystem = new CapabilityType("Server.Capability.Filesystem");
    public static final CapabilityType Console = new CapabilityType("Server.Capability.Console");
    
    public static CapabilityType valueOf( String code ) {
        return REGISTRY.get(code);
    }
    
    public static CapabilityType[] values() {
        return REGISTRY.values().toArray( new CapabilityType[REGISTRY.size()] );
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapabilityType)) return false;

        CapabilityType that = (CapabilityType) o;

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
