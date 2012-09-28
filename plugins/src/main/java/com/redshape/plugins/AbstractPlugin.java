package com.redshape.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/14/11
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPlugin implements IPlugin {
    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    @Override
    public <V> V getAttribute(String name) {
        return (V) this.attributes.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }
}
