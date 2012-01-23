package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.DAOException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IExecutionRequest<T> {

    public T execute() throws DAOException;

    public IExecutionRequest offset( int from );

    public IExecutionRequest limit( int count );

}
