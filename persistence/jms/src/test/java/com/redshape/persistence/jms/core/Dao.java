package com.redshape.persistence.jms.core;

import com.redshape.persistence.dao.AbstractDao;
import com.redshape.persistence.dao.query.IQueryBuilder;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.jms
 * @date 1/25/12 {4:28 PM}
 */
public class Dao extends AbstractDao<EntityRecord> implements IDao {

    public Dao(IQueryExecutorService executor, IQueryBuilder builder) {
        super(EntityRecord.class, executor, builder);
    }
}
