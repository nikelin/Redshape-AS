package com.redshape.persistence.dao.query.executors.services;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.executors.IExecutorResult;
import com.redshape.persistence.entities.IEntity;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IQueryExecutorService {

    public <T extends IEntity>
        IExecutorResult<T> execute( IQuery query ) throws DAOException;

    public <T extends IEntity>
        IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                          String queryName,
                                                          Map<String, Object> params )
            throws DAOException;

    public <T extends IEntity>
        IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                          String queryName,
                                                          Map<String, Object> params,
                                                          int offset )
            throws DAOException;

    public <T extends IEntity>
        IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                              String queryName,
                                              Map<String, Object> params,
                                              int offset, int limit )
            throws DAOException;


}
