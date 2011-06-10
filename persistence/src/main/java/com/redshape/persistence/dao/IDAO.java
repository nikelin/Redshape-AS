package com.redshape.persistence.dao;

import com.redshape.persistence.entities.IEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IDAO<T extends IEntity> {

    public List<T> executeNamedQuery(String queryName, Map<String, Object> keyValues) throws DAOException;

    public List<T> executeNamedQuery(String queryName, Map<String, Object> keyValues, int offset) throws DAOException;

    public List<T> executeNamedQuery(String queryName, Map<String, Object> keyValues, int offset, int limit) throws DAOException;

    public T save(T object) throws DAOException;

    public void save(Collection<T> object) throws DAOException;

    public void persist( T record ) throws DAOException;
    
    public T update(T object) throws DAOException;

    public void update(Collection<T> object) throws DAOException;

    public void removeAll() throws DAOException;

    public void remove(T object) throws DAOException;
    
    public void remove( Collection<T> object) throws DAOException;

    public T findById(Long id) throws DAOException;

    public List<T> findAll() throws DAOException;

    public List<T> findAll(int offset) throws DAOException;

    public List<T> findAll(int offset, int limit) throws DAOException;

    public Long count() throws DAOException;

}

