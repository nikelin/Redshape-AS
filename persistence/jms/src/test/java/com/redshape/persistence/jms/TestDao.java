package com.redshape.persistence.jms;

import com.redshape.persistence.dao.AbstractDao;
import com.redshape.persistence.dao.query.IQueryBuilder;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.jms
 * @date 1/25/12 {4:28 PM}
 */
public class TestDao extends AbstractDao<TestEntity> implements ITestDao {

    public TestDao(IQueryExecutorService executor, IQueryBuilder builder) {
        super(TestEntity.class, executor, builder);
    }
}
