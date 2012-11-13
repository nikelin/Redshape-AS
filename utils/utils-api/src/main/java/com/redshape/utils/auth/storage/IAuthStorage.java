/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.utils.auth.storage;

import com.redshape.utils.auth.IIdentity;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 19, 2010
 * Time: 6:16:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IAuthStorage {

    /**
     * Сохранить сущность identity в хранилище с идентификатором id
     * @param id
     * @param identity
     * @return
     */
    public void save( Object id, IIdentity identity );

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
    public void remove( Object id );

    /**
     * Remove given entity from storage
     * @param identity
     * @return
     */
    public void remove( IIdentity identity );

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
    public boolean find( Object id );

}
