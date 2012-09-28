package com.redshape.utils.auth.storage;

import com.redshape.utils.auth.IIdentity;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory storage for authenticated entities
 *
 * @author nikelin
 */
public class MemoryStorage implements IAuthStorage {
    private Map<Object, IIdentity> store = new HashMap<Object, IIdentity>();

    public void save( Object id, IIdentity identity ) {
        this.store.put( id, identity );
    }

    @SuppressWarnings("unchecked")
	public <T extends IIdentity> T get( Object id ) {
        return (T) this.store.get(id);
    }

    public void remove( Object id ) {
        this.store.remove(id);
    }

    @Override
    public void remove( IIdentity identity ) {
        this.store.remove( identity );
    }

    @Override
    public boolean find( Object id ) {
        return this.store.containsKey(id);
    }

    @SuppressWarnings("unchecked")
	public <T extends IIdentity> Map<Object, T> getIdentities() {
        return (Map<Object, T>) this.store;
    }

}
