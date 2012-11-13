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

package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.entities.IEntity;

import java.util.List;

/**
 * Represents data request objects which can be used by
 * DAO to provide top-end user with ability of affecting on
 * the result containment.
 * @param <T>
 * @author Cyril A. Karpenko <self@nikelin.ru>
 */
public interface IExecutionRequest<T extends IEntity> {

    /**
     * Provides end-user with ability to access underlying query
     * object to modify some predefined values (sorting way, grouping, etc.).
     * @return
     * @throws DAOException
     */
    public IQuery query();

    /**
     * Allow user to use result values in a non type-safe
     * way.
     * @param <Z>
     * @return
     * @throws DAOException
     */
    public <Z> List<Z> listValue() throws DAOException;

    /**
     * Return single result value as a result which type can be defined
     * @param <Z>
     * @return
     * @throws DAOException
     */
    public <Z> Z resultValue() throws DAOException;

    /**
     * Return single result record-value
     * @return
     * @throws DAOException
     */
    public T result() throws DAOException;

    /**
     * Return list of a resulting records
     * @return
     * @throws DAOException
     */
    public List<T> list() throws DAOException;

    /**
     * Limit target records range from the bottom
     * @param from
     * @return
     */
    public IExecutionRequest<T> offset( int from );

    /**
     * Limit result records count
     * @param count
     * @return
     */
    public IExecutionRequest<T> limit( int count );

    /**
     * Calculate query evaluation result set size ( COUNT(X) )
     * @return
     * @throws DAOException
     */
    public int count() throws DAOException;

}
