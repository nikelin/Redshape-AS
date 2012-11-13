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

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.executors.IStaticQueryExecutor;
import com.redshape.persistence.entities.IEntity;

import java.util.Map;

public interface IIndexBuilder {

    public Map<String, byte[]> buildIndex(IEntity entity) throws IndexBuilderException;

    public void setSerializer(ISerializer serializer);

    public ISerializer getSerializer();

    public IStaticQueryExecutor getStaticQueryExecutor(IQuery query);

    public void setEntityClass(Class<? extends IEntity> clazz);

    public Class<? extends IEntity> getEntityClass();

}