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
