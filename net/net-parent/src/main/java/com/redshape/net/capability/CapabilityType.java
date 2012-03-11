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
