package com.redshape.net.capability;

import com.redshape.utils.IEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 13:20
 */
public class CapabilityAttribute implements IEnum<String>{
    private static final Map<String, CapabilityAttribute> REGISTRY = new HashMap<String, CapabilityAttribute>();

    private String code;

    public CapabilityAttribute(String code) {
        this.code = code;
        REGISTRY.put(code, this);
    }

    public static CapabilityAttribute valueOf( String name ) {
        return REGISTRY.get(name);
    }
    
    public static CapabilityAttribute[] values() {
        return REGISTRY.values().toArray( new CapabilityAttribute[REGISTRY.size()] );
    }
    
    @Override
    public String name() {
        return this.code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapabilityAttribute)) return false;

        CapabilityAttribute that = (CapabilityAttribute) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
