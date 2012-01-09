package com.redshape.plugins.starters;

import com.redshape.utils.IEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class EngineType implements IEnum<String> {

    private String name;

    protected EngineType(String name) {
        this.name = name;
        REGISTRY.put(name, this);
    }

    @Override
    public String name() {
        return this.name;
    }

    public static final Map<String, EngineType> REGISTRY = new HashMap<String, EngineType>();
    
    public static final EngineType Java = new EngineType("java");
    public static final EngineType Python = new EngineType("python");
    public static final EngineType Ruby = new EngineType("ruby");
    
    public static EngineType valueOf( String name ) {
        return REGISTRY.get(name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return this.name().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }

        if ( obj == null || !( obj instanceof EngineType ) ) {
            return false;
        }

        return ((EngineType) obj).name().toLowerCase().equals( this.name() );
    }
}
