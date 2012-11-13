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

package com.redshape.persistence.core;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.AbstractDao;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.IQueryBuilder;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityDao extends AbstractDao<EntityRecord> implements IEntityDAO {

    public EntityDao(IQueryExecutorService executor, IQueryBuilder builder) {
        super(EntityRecord.class, executor, builder);
    }

    @Override
    public List<EntityRecord> findByIds( Long[] ids ) throws DAOException {
        IQuery query = this.getBuilder().query(EntityRecord.class);
        query.where(
            this.getBuilder().in( this.getBuilder().reference("id"),
                    this.getBuilder().array( this.getBuilder().scalar(ids) ) )
        );

        return this.execute(query).list();
    }
    
}
