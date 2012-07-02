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
        REGISTRY.put( contextId, this );
    }

    public static final ContextId AJAX = new ContextId("ContextId.AJAX");
    public static final ContextId JSP = new ContextId("ContextId.JSP");
    public static final ContextId XSL = new ContextId("ContextId.JSP");

    public static ContextId valueOf( String value ) {
        return REGISTRY.get( value );
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
