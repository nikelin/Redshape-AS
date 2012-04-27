package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.entities.IEntity;

import java.util.List;

/**
 * Represents data request objects which can be used by
 * DAO to provide top-end user with ability of affecting on
 * the result containment.
 * @param <T>
 * @author Cyril A. Karpenko <self@nikelin.ru>
 */
public interface IExecutionRequest<T extends IEntity> {

    /**
     * Provides end-user with ability to access underlying query
     * object to modify some predefined values (sorting way, grouping, etc.).
     * @return
     * @throws DAOException
     */
    public IQuery query() throws DAOException;

    /**
     * Allow user to use result values in a non type-safe
     * way.
     * @param <Z>
     * @return
     * @throws DAOException
     */
    public <Z> List<Z> listValue() throws DAOException;

    /**
     * Return single result value as a result which type can be defined
     * @param <Z>
     * @return
     * @throws DAOException
     */
    public <Z> Z resultValue() throws DAOException;

    /**
     * Return single result record-value
     * @return
     * @throws DAOException
     */
    public T result() throws DAOException;

    /**
     * Return list of a resulting records
     * @return
     * @throws DAOException
     */
    public List<T> list() throws DAOException;

    /**
     * Limit target records range from the bottom
     * @param from
     * @return
     */
    public IExecutionRequest<T> offset( int from );

    /**
     * Limit result records count
     * @param count
     * @return
     */
    public IExecutionRequest<T> limit( int count );

}
