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
public interface AuthStorage {

    /**
     * Сохранить сущность identity в хранилище с идентификатором id
     * @param id
     * @param identity
     * @return
     */
    public AuthStorage save( Object id, IIdentity identity );

    /**
     * Запросить сущность из хранилища
     * 
     * @param id
     * @return
     */
    public <T extends IIdentity> T get( Object id );

    /**
     * Удалить сущность из хранилища
     *
     * @param id
     * @return
     */
    public AuthStorage remove( Object id );

    public AuthStorage remove( IIdentity identity );

    /**
     * Получить все сущность из данного хранилища
     * @return
     */
    public <T extends IIdentity> Map<Object, T> getIdentities();

    /**
     * Существует ли сущность в хранилище
     * @param id
     * @return
     */
    public boolean isExists( Object id );

}
