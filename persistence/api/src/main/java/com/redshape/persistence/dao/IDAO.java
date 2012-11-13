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

package com.redshape.persistence.dao;

import com.redshape.persistence.dao.query.executors.IExecutionRequest;
import com.redshape.persistence.entities.IEntity;

import java.util.Collection;

/**
 * @param <T>
 * @author Cyril A. Karpenko <self@nikelin.ru>
 */
public interface IDAO<T extends IEntity> {
    
    public T findById( Long id ) throws DAOException;
    
    public IExecutionRequest<T> findAll() throws DAOException;
    
    public T save(T object) throws DAOException;

    public void save(Collection<T> object) throws DAOException;

    public void removeAll() throws DAOException;

    public void remove(T object) throws DAOException;
    
    public void remove( Collection<T> object) throws DAOException;

    public Long count() throws DAOException;

    public Class<? extends T> getEntityClass();

}

