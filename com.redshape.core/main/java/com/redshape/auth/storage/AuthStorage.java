package com.redshape.auth.storage;

import com.redshape.auth.IIdentity;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 19, 2010
 * Time: 6:16:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuthStorage<T extends IIdentity> {

    /**
     * Сохранить сущность identity в хранилище с идентификатором id
     * @param id
     * @param identity
     * @return
     */
    public AuthStorage save( Object id, T identity );

    /**
     * Запросить сущность из хранилища
     * 
     * @param id
     * @return
     */
    public T get( Object id );

    /**
     * Удалить сущность из хранилища
     *
     * @param id
     * @return
     */
    public AuthStorage remove( Object id );

    public AuthStorage remove( T identity );

    /**
     * Получить все сущность из данного хранилища
     * @return
     */
    public Map<Object, T> getIdentities();

    /**
     * Время жизни сущностей в хранилище
     * @param time
     * @return
     */
    public AuthStorage setLifeTime( long time );

    /**
     * Существует ли сущность в хранилище
     * @param id
     * @return
     */
    public boolean isExists( Object id );

}
