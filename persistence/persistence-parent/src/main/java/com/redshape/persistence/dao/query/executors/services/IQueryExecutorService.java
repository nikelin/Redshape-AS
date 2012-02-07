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

    public <T extends IEntity> IExecutorResult<T> execute( IQuery query ) throws DAOException;

}
