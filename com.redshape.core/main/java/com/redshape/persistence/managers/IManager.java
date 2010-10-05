package com.redshape.persistence.managers;

import com.redshape.persistence.entities.IEntity;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 5:48:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IManager {
    public <T extends IEntity> T createNew() throws ManagerException, IllegalAccessException, InstantiationException;

    public <T extends IEntity> T save(T object ) throws ManagerException;

    public <T extends IEntity> T save( T object, boolean doNestedSave ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> T update( T record ) throws ManagerException;

    public void removeAll() throws ManagerException;

    public void remove( IEntity object ) throws ManagerException;

    public <T extends IEntity> T find( T object ) throws ManagerException;

    public <T extends IEntity> T find( Integer id ) throws ManagerException;

    public Class<? extends IEntity> getEntityClass();

    public <T extends IEntity> Collection<T> findBy( String name, Object value ) throws ManagerException;

    public IEntity getOrCreateBy( String name, Object value ) throws ManagerException;

    public boolean isExists( IEntity object ) throws ManagerException;

    public <T extends IEntity> T findOneBy( String name, Object value ) throws ManagerException;

    public Collection<? extends IEntity> findAll() throws ManagerException;

    public Collection<? extends IEntity> whereIn( Collection<Integer> ids ) throws ManagerException;

    public Collection<? extends IEntity> whereIn( Integer[] ids ) throws ManagerException;

    public Collection<? extends IEntity> whereIn( String name, Integer[] ids ) throws ManagerException;

    public Collection<Integer> getIdsBy( String name, Object value ) throws ManagerException;

    public Collection<Integer> getIds( Integer count ) throws ManagerException;

    public Collection<Integer> getIds( Integer from, Integer count ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> Collection<T> findMatches( Map<String, Object> constrain ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> Collection<T> findMatches( Map<String, Object> constrain, String[] operators ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> Collection<T> findMatches( String[] names, Object[] values ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> Collection<T> findMatches( String[] names, Object[] values, String[] operators ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> T findOneMatched( String[] names, Object[] values ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> T findOneMatched( String[] names, Object[] values, String[] operators ) throws ManagerException;

    @Deprecated
    public <T extends IEntity> T findOneMatched( String[] names, Object[] values, String[] operators, String[] select ) throws ManagerException;

    public Query createQuery( String query ) throws ManagerException;

    public Query createQuery( String query, String names[], Object values[] ) throws ManagerException;

    public Collection<?> executeQuery( String query ) throws ManagerException;

    public Collection<?> executeQuery( String query, String[] names, Object... values ) throws ManagerException;

    public String getEntityName();
}
