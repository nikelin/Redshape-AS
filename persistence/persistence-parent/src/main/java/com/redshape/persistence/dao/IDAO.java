package com.redshape.persistence.dao;

import com.redshape.persistence.dao.query.executors.IExecutionRequest;
import com.redshape.persistence.entities.IEntity;

import java.util.Collection;

/**
 * @param <T>
 * @author Cyril A. Karpenko <self@nikelin.ru>
 */
public interface IDAO<T extends IEntity> {
    
    public T findById( Long id ) throws DAOException;
    
    public IExecutionRequest<T> findAll() throws DAOException;
    
    public T save(T object) throws DAOException;

    public void save(Collection<T> object) throws DAOException;

    public void removeAll() throws DAOException;

    public void remove(T object) throws DAOException;
    
    public void remove( Collection<T> object) throws DAOException;

    public Long count() throws DAOException;

    public Class<? extends T> getEntityClass();

}

