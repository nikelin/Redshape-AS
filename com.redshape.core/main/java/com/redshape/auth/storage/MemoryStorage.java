package com.redshape.auth.storage;

import com.redshape.auth.IIdentity;

import java.util.HashMap;
import java.util.Map;

/**
 * Простое хранилище, распологающее авторизованных сущностей в памяти
 *
 * @author nikelin
 */
public class MemoryStorage<T extends IIdentity> implements AuthStorage<T> {
    private long lifeTime;
    private Map<Object, T> store = new HashMap<Object, T>();

    public MemoryStorage setLifeTime( long time ) {
        this.lifeTime = time;
        return this;
    }

    public MemoryStorage save( Object id, T identity ) {
        this.store.put( id, identity );
        return this;
    }

    public T get( Object id ) {
        return this.store.get(id);
    }

    public MemoryStorage remove( Object id ) {
        this.store.remove(id);
        return this;
    }

    public MemoryStorage remove( T identity ) {
        this.store.remove( identity );
        return this;
    }

    public boolean isExists( Object id ) {
        return this.store.containsKey(id);
    }

    public Map<Object, T> getIdentities() {
        return this.store;
    }

}
