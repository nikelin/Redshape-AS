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

package com.redshape.persistence.dao.query.executors.services;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.executors.result.IExecutorResult;
import com.redshape.persistence.dao.query.executors.result.IExecutorResultFactory;
import com.redshape.persistence.entities.IEntity;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IQueryExecutorService {

    public void setResultObjectsFactory( IExecutorResultFactory factory );

    public <T extends IEntity> IExecutorResult<T> execute( IQuery<T> query ) throws DAOException;

}
