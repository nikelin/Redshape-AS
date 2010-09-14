package com.redshape.persistence.managers;

import com.redshape.persistence.Provider;
import com.redshape.persistence.entities.IEntity;
import com.redshape.persistence.entities.Nullable;
import com.redshape.utils.BaseReflectionUtil;
import com.redshape.utils.ReflectionUtil;
import com.redshape.utils.beans.Property;
import com.redshape.utils.beans.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import java.beans.IntrospectionException;
import java.util.*;

public class Manager implements IManager {
    private Class<? extends IEntity> entityClass;
    
    private static final Logger log = Logger.getLogger( com.redshape.persistence.managers.Manager.class );
    private ReflectionUtil reflection = new BaseReflectionUtil();

    public <T extends IEntity> Manager( Class<T> entityClass ) {
        this.entityClass = entityClass;
    }

    @Override
    public <T extends IEntity> T createNew() throws ManagerException, IllegalAccessException, InstantiationException {
        T object = (T) this.entityClass.newInstance();

        this.save(object);

        return object;
    }

    protected void rollbackTransaction() throws ManagerException {
        if ( this.getManager().getTransaction().isActive() ) {
            this.getManager().getTransaction().rollback();
        }
    }

    protected void beginTransaction() throws ManagerException {
        if ( !this.getManager().getTransaction().isActive() ) {
            this.getManager().getTransaction().begin();
        }
    }

    protected void commitTransaction() throws ManagerException {
        if ( !this.getManager().getTransaction().isActive() ) {
            this.beginTransaction();
        }

        this.getManager().getTransaction().commit();
    }

    protected ReflectionUtil getReflection() {
        return this.reflection;
    }

    protected EntityManager getManager() throws ManagerException {
        try {
            return Provider.getManager();
        } catch ( Throwable e ) {
            throw new ManagerException( e.getMessage() );
        }
    }

    @Override
    public <T extends IEntity> T save(T object ) throws ManagerException {
        return this.save( object, false );
    }

    /**
     * @TODO Optimize
     * @param object
     * @param doNestedSave
     * @param <T>
     * @return
     * @throws ManagerException
     */
    @Override
    public <T extends IEntity> T save( T object, boolean doNestedSave ) throws ManagerException {
        try {
            if ( doNestedSave ) {
                this._saveNestedUnsavedEntities( object );
            }

            IManager m = object.getDAO();
            T record = m.find( object );

            log.info( "Object class: " + object.getClass().getName() );
            log.info( record == null ? "Not found" : "Id of object: " + record.getId()  );
            if ( record != null ) {
                object.setId( record.getId() );

                this.beginTransaction();
                this.getManager().merge(object);
                this.getManager().flush();
                this.commitTransaction();
            } else {
                this.beginTransaction();
                this.getManager().persist(object);
                this.getManager().flush();
                this.commitTransaction();
            }

            return object;
        } catch (Throwable e) {
            log.error( e.getMessage(), e );
            throw new ManagerException( e.getMessage() );
        }
    }


    private Collection _saveNestedUnsavedEntities( Collection<IEntity> list ) throws ManagerException {
        try {
            Collection<IEntity> nList = list.getClass().newInstance();
            for ( Object item : list ) {
                if ( IEntity.class.isAssignableFrom( item.getClass() ) && item != null ) {
                    final IEntity toSave = (IEntity) item;
                    try {
                        nList.add( this.save( toSave, true ) );
                    } catch ( ManagerException e ) {
                        if ( e.getCause().equals(ConstraintViolationException.class) ) {
                            nList.add( this.find( toSave ) );
                        }
                    }
                }
            }

            list.clear();
            list.addAll( nList );

            return list;
        } catch ( Throwable e ) {
            throw new ManagerException();
        }
    }

    private static boolean isNestedEntity( Class clazz ) {
        return Collection.class.isAssignableFrom( clazz ) ||
            IEntity.class.isAssignableFrom( clazz );
    }

    private boolean isNulled( Object object ) {
        return object == null || ( Nullable.class.isAssignableFrom( object.getClass() ) && ( (Nullable) object ).isNull() );
    }

    private void _saveNestedUnsavedEntities( IEntity object ) throws ManagerException {
        try {
            Map<String, Property> properties = PropertyUtils.getInstance().getPropertiesMap( object.getClass() );
            for ( String propertyName : properties.keySet()  ) {
               try {
                    Object nested = properties.get(propertyName).get(object);

                    if ( isNestedEntity( properties.get(propertyName).getType() ) && !isNulled(nested) ) {
                        log.info("Field " + propertyName + " value is: " + nested );
                        if ( Collection.class.isAssignableFrom( nested.getClass() ) ) {
                            nested = this._saveNestedUnsavedEntities( (Collection) nested );
                        } else {
                            nested = this.save( (IEntity) nested, true );
                        }

                        properties.get(propertyName).set( object, nested );
                    }
                } catch ( Throwable e ) {
                    log.error( e.getMessage(), e );
                }
            }
        } catch ( IntrospectionException e ) {
            throw new ManagerException();
        }
    }

    @Override
    @Deprecated
    public <T extends IEntity> T update( T record ) throws ManagerException {
        try {
            return this.getManager().merge(record);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ManagerException();
        }
    }

    @Override
    public void removeAll() throws ManagerException {
        try {
            this.getManager().createNativeQuery( "truncate " + this.getEntityClass().getAnnotation(javax.persistence.Entity.class).name() ).executeUpdate();
        } catch ( Throwable e ) {
            throw new ManagerException( e.getMessage() );
        }
    }

    @Override
    public void remove( IEntity object ) throws ManagerException {
        try {
            this.beginTransaction();
            this.getManager().remove( object );
            this.commitTransaction();
        } catch ( Throwable e ) {
            throw new ManagerException( e.getMessage() );
        }
    }

    @Override
    public <T extends IEntity> T find( T object ) throws ManagerException {
       return object.getId() != null ? (T) this.find( object.getId() )
                                            : null;
    }
    @Override
    public <T extends IEntity> T find( Integer id ) throws ManagerException {
        try {
            return (T) this.getManager().find( this.getEntityClass(), id );
        } catch ( Throwable e ) {
            throw new ManagerException();
        }
    }

    @Override
    public Class<? extends IEntity> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public <T extends IEntity> List<T> findBy( String name, Object value ) throws ManagerException {
        try {
            return (List<T>) this.executeQuery( this.buildMatchesQuery( new String[] { name }, new Object[] { value }, new String[] { "=" } ) );
        } catch ( Throwable e ) {
            throw new ManagerException();
        }
    }

    @Override
    public IEntity getOrCreateBy( String name, Object value ) throws ManagerException {
        try {
            IEntity object = this.findOneBy(name, value);
            if ( object == null ) {
                object = this.getEntityClass().newInstance();
                PropertyUtils.getInstance().getProperty( object.getClass(), name ).set(object, value);

                object.save();
            }

            return object;
        } catch ( Throwable e ) {
            throw new ManagerException( e.getMessage() );
        }
    }

    @Override
    public boolean isExists( IEntity object ) throws ManagerException {
        try {
            return null != this.find( object.getId() );
        } catch ( Throwable e ) {
            return false;
        }
    }

    @Override
    public <T extends IEntity> T findOneBy( String name, Object value ) throws ManagerException {
        try {
            List<T> result = this.getManager()
                                 .createQuery("from " + this.getEntityName() + " r where " + name + " = :value ")
                                    .setParameter("value", value)
                                    .getResultList();
                               
            if ( result.size() == 0 ) {
                return null;
            }

            return result.get(0);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ManagerException( e.getMessage() );
        }
    }

    @Override
    public List<? extends IEntity> findAll() throws ManagerException {
        return this.getManager().createQuery("select t from " + this.getEntityName() + " t" ).getResultList();
    }

    @Override
    public List<? extends IEntity> whereIn( List<Integer> ids ) throws ManagerException {
        return this.whereIn( ids.toArray( new Integer[ids.size()] ) );
    }

    @Override
    public List<? extends IEntity> whereIn( Set<Integer> ids ) throws ManagerException {
        return this.whereIn( ids.toArray( new Integer[ids.size()]));
    }

    @Override
    public List<? extends IEntity> whereIn( Integer[] ids ) throws ManagerException {
        /**
         * @TODO необходимо получать имя идентификатора для конкретной сущности, ибо
         * он может быть различным
         */
        return this.whereIn( "id", ids );
    }

    

    @Override
    public List<? extends IEntity> whereIn( String name, Integer[] ids ) throws ManagerException {
        return this.getManager().createQuery("from " + this.getEntityName() + " where " + name + " in (:ids)")
                                    .setParameter("ids", ids )
                                        .getResultList();


    }

    @Override
    public List<Integer> getIdsBy( String name, Object value ) throws ManagerException {
        return this.getManager().createQuery(
            this.buildMatchesQuery(new String[] {name}, new Object[] {value}, new String[] {"="}, new String[] { "id" } )
        ).getResultList();
    }

    @Override
    public List<Integer> getIds( Integer count ) throws ManagerException {
        return this.getIds(0, count);
    }

    @Override
    public List<Integer> getIds( Integer from, Integer count ) throws ManagerException {
        return this.getManager().createQuery(
            this.buildMatchesQuery( new String[] {}, new Object[] {}, new String[] {}, new String[] { "id" }, from, count )
        ).getResultList();
    }

    @Override
    @Deprecated
    public <T extends IEntity> List<T> findMatches( Map<String, Object> constrain ) throws ManagerException {
        String[] operators = new String[ constrain.size() ];
        Arrays.fill( operators, "=" );
        
        return this.findMatches( constrain, operators  );
    }

    @Override
    @Deprecated
    public <T extends IEntity> List<T> findMatches( Map<String, Object> constrain, String[] operators ) throws ManagerException {
        return this.findMatches( constrain.keySet().toArray( new String[constrain.size()]), constrain.values().toArray( new Object[constrain.size()]), operators );
    }

    @Override
    @Deprecated
    public <T extends IEntity> List<T> findMatches( String[] names, Object[] values ) throws ManagerException {
        return this.findMatches( names, values, this.getDefaultOperators(values.length));
    }

    @Override
    @Deprecated
    public <T extends IEntity> List<T> findMatches( String[] names, Object[] values, String[] operators ) throws ManagerException {
        try {
            return (List<T>) this.getManager().createQuery( this.buildMatchesQuery( names, values, operators ) ).getResultList();
        } catch ( Throwable e ) {
            throw new ManagerException();
        }
    }

    private String[] getDefaultOperators( int count ) {
        String[] operators = new String[ count ];
        Arrays.fill( operators, "=" );

        return operators;
    }

    @Override
    @Deprecated
    public <T extends IEntity> T findOneMatched( String[] names, Object[] values ) throws ManagerException {
        return this.<T>findOneMatched( names, values, this.getDefaultOperators(values.length) );
    }

    @Override
    @Deprecated
    public <T extends IEntity> T findOneMatched( String[] names, Object[] values, String[] operators ) throws ManagerException {
        return this.<T>findOneMatched( names, values, operators, new String[] {} );
    }

    @Override
    @Deprecated
    public <T extends IEntity> T findOneMatched( String[] names, Object[] values, String[] operators, String[] select ) throws ManagerException {
        try {
            T result;
            try {
                result = (T) this.getManager().createQuery( this.buildMatchesQuery(names, values, operators, select, 1 ) ).getSingleResult();
            } catch ( NoResultException e ) {
                result = null;
            }

            return result;
        } catch ( Throwable e ) {
            log.error( "Select query exception", e );
            throw new ManagerException();
        }
    }


    @Deprecated
    public String buildMatchesQuery( String[] names, Object[] values, String[] operators ) throws ManagerException {
        return this.buildMatchesQuery( names, values, operators, new String[] {} );
    }

    @Deprecated
    public String buildMatchesQuery( String[] names, Object[] values, String[] operators, String[] select, Integer count ) {
        return this.buildMatchesQuery( names, values, operators, select, 0, count );
    }

    @Deprecated
    public String buildMatchesQuery( String[] names, Object[] values, String[] operators, String[] select ) {
        return this.buildMatchesQuery( names, values, operators, select, 0 );
    }

    @Deprecated
    private String buildMatchesQuery( String[] names, Object[] values, String[] operators, String[] select, Integer from, Integer limit ) throws IllegalArgumentException {
        if ( names.length != values.length || values.length != operators.length ) {
            throw new IllegalArgumentException("Names count must be equals to values count");
        }

        StringBuilder query = new StringBuilder();

        if ( select.length != 0 ) {
            query.append("select ");
            for ( int i = 0; i < select.length; i++ ) {
                if ( !select[i].startsWith("e.") ) {
                    query.append("e.");
                }

                query.append( select[i] );

                if ( i < select.length - 1 ) {
                    query.append( ", " );
                }
            }
        }

        query.append( " from " )
             .append( this.getEntityName() )
             .append(" as e ");

        if ( names.length != 0 ) {
            query.append(" where ");
            for ( int i = 0; i < names.length; i++ ) {
                query.append( "e." + names[i] )
                    .append(" ")
                    .append( operators[i] )
                    .append(" ");

                query.append( this.escapeQueryValue( values[i] ) );

                if ( i != names.length - 1  ) {
                    query.append(" AND ");
                }
            }
        }

        return query.toString();
    }

    private String escapeQueryValue( Object value ) {
        if ( value == null ) {
            return "null";
        } else if ( String.class.isAssignableFrom( value.getClass() ) || Enum.class.isAssignableFrom( value.getClass() ) ) {
            return "'" + value.toString()  + "'";
        } else {
            return String.valueOf(value);
        }
    }

    public List<?> executeQuery( String query ) throws ManagerException {
        return this.executeQuery(query, new String[] {}, new Object[] {});
    }

    public List<?> executeQuery( String query, String[] names, Object... values ) throws ManagerException {
        return this.createQuery(query, names, values).getResultList();
    }

    public Query createQuery( String query ) throws ManagerException {
        return this.createQuery( query, new String[] {}, new Object[] {} );
    }

    public Query createQuery( String query, String names[], Object values[] ) throws ManagerException {
       Query q = this.getManager().createQuery( query );

       for ( int i = 0; i < names.length; i++ ) {
           q.setParameter( names[i], values[i] );
       }

       return q;
    }

    public static String getEntityName( IEntity entity ) {
        return getEntityName( entity.getClass() );
    }

    @Override
    public String getEntityName() {
        return getEntityName( this.getEntityClass() );
    }


    public static String getEntityName( Class<? extends IEntity> clazz ) {
        javax.persistence.Entity annon = clazz.getAnnotation( javax.persistence.Entity.class );
        if ( annon != null ) {
            String result = annon.name();

            if ( !result.isEmpty() ) {
                return result;
            }
        }

        return clazz.getCanonicalName();
    }

    protected <T extends IEntity> boolean isTargetEntity( T object ) {
        return this.isTargetEntity( object.getClass() );
    }

    protected <T extends IEntity> boolean isTargetEntity( Class<T> object ) {
        return this.getEntityClass().equals( object );
    }

}