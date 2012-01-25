package com.redshape.persistence;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.jpa.AbstractDao;
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
public class TestDao extends AbstractDao<TestEntity> implements ITestDAO {

    public TestDao(IQueryExecutorService executor, IQueryBuilder builder) {
        super(TestEntity.class, executor, builder);
    }

    @Override
    public List<TestEntity> findByIds( Long[] ids ) throws DAOException {
        IQuery query = this.getBuilder().query(TestEntity.class);
        query.where(
            this.getBuilder().in( this.getBuilder().reference("id"),
                    this.getBuilder().array( this.getBuilder().scalar(ids) ) )
        );

        return this.execute(query).list();
    }
    
}
