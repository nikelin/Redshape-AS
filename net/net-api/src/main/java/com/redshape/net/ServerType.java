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
 * @date 12:58
 */
public class ServerType implements IEnum<String> {
    private static final Map<String, ServerType> REGISTRY = new HashMap<String, ServerType>();

    private String code;

    protected ServerType( String code ) {
        this.code = code;
        REGISTRY.put(code, this);
    }

    public static final ServerType UNIX = new ServerType("Server.Type.UNIX");
    public static final ServerType WINDOWS = new ServerType("Server.Type.WINDOWS");
    public static final ServerType UNKNOWN = new ServerType("Server.Type.UNKNOWN");

    public static ServerType valueOf( String name ) {
        return REGISTRY.get(name);
    }

    public static ServerType[] values() {
        return REGISTRY.values().toArray( new ServerType[REGISTRY.size()] );
    }

    @Override
    public String name() {
        return this.code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerType)) return false;

        ServerType that = (ServerType) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    public static ServerType detectFamily( String platformName ) {
        final String normalizedName = platformName.toLowerCase();
        if ( normalizedName.contains("windows") ) {
            return WINDOWS;
        } else if ( normalizedName.contains("freebsd") || normalizedName.contains("linux") || normalizedName.contains("unix")  ) {
            return UNIX;
        }

        return UNKNOWN;
    }

}
