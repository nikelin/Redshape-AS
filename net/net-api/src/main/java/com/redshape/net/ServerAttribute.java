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
