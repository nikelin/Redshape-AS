package com.redshape.auth.storage;

import com.redshape.auth.IIdentity;

import java.util.HashMap;
import java.util.Map;

/**
 * Простое хранилище, распологающее авторизованных сущностей в памяти
 *
 * @author nikelin
 */
public class MemoryStorage implements AuthStorage {
    private Map<Object, IIdentity> store = new HashMap<Object, IIdentity>();

    public MemoryStorage save( Object id, IIdentity identity ) {
        this.store.put( id, identity );
        return this;
    }

    @SuppressWarnings("unchecked")
	public <T extends IIdentity> T get( Object id ) {
        return (T) this.store.get(id);
    }

    public MemoryStorage remove( Object id ) {
        this.store.remove(id);
        return this;
    }

    public MemoryStorage remove( IIdentity identity ) {
        this.store.remove( identity );
        return this;
    }

    public boolean isExists( Object id ) {
        return this.store.containsKey(id);
    }

    @SuppressWarnings("unchecked")
	public <T extends IIdentity> Map<Object, T> getIdentities() {
        return (Map<Object, T>) this.store;
    }

}
