package com.redshape.ui.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractModelData implements IModelData {
    private Map<String, Object> values = new HashMap<String, Object>();
    private boolean isDirty;
    
    @Override
    public void set( String name, Object value ) {
        this.values.put(name, value);
        this.makeDirty(true);
    }

    @Override
    public void makeDirty( boolean value ) {
    	this.isDirty = value;
    }
    
    @Override
    public boolean isDirty() {
    	return this.isDirty;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public <V> V get( String name ) {
        return (V) this.values.get(name);
    }

    @SuppressWarnings("unchecked")
	protected <V> Map<String, V> getAll() {
        return (Map<String, V>) this.values;
    }

}
