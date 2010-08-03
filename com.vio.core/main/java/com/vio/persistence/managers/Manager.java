package com.vio.persistence.managers;

import com.vio.persistence.Provider;
import com.vio.persistence.entities.Entity;
import com.vio.persistence.entities.Nullable;
import com.vio.utils.BaseReflectionUtil;
import com.vio.utils.ReflectionUtil;
import com.vio.utils.beans.Property;
import com.vio.utils.beans.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Manager {
    private Class<? extends Entity> entityClass;
    
    private static final Logger log = Logger.getLogger( com.vio.persistence.managers.Manager.class );
    private ReflectionUtil reflection = new BaseReflectionUtil();

    public <T extends Entity> Manager( Class<T> entityClass ) {
        this.entityClass = entityClass;
    }

    public <T extends Entity> T createNew() throws ManagerException, IllegalAccessException, InstantiationException {
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

    public <T extends Entity> T save(T object ) throws ManagerException {
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
    public <T extends Entity> T save( T object, boolean doNestedSave ) throws ManagerException {
        try {
            if ( doNestedSave ) {
                this._saveNestedUnsavedEntities( object );
            }

            Manager m = object.getDAO();
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


    private Collection _saveNestedUnsavedEntities( Collection<Entity> list ) throws ManagerException {
        try {
            Collection<Entity> nList = list.getClass().newInstance();
            for ( Object item : list ) {
                if ( Entity.class.isAssignableFrom( item.getClass() ) && item != null ) {
                    final Entity toSave = (Entity) item;
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
            Entity.class.isAssignableFrom( clazz );
    }

    private boolean isNulled( Object object ) {
        return object == null || ( Nullable.class.isAssignableFrom( object.getClass() ) && ( (Nullable) object ).isNull() );
    }

    private void _saveNestedUnsavedEntities( Entity object ) throws ManagerException {
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
                            nested = this.save( (Entity) nested, true );
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

    @Deprecated
    public <T extends Entity> T update( T record ) throws ManagerException {
        try {
            return this.getManager().merge(record);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ManagerException();
        }
    }

    public void removeAll() throws ManagerException {
        try {
            this.getManager().createNativeQuery( "truncate " + this.getEntityClass().getAnnotation(javax.persistence.Entity.class).name() ).executeUpdate();
        } catch ( Throwable e ) {
            throw new ManagerException( e.getMessage() );
        }
    }

    public void remove( Entity object ) throws ManagerException {
        try {
            this.beginTransaction();
            this.getManager().remove( object );
            this.commitTransaction();
        } catch ( Throwable e ) {
            throw new ManagerException( e.getMessage() );
        }
    }

    public <T extends Entity> T find( T object ) throws ManagerException {
       return object.getId() != null ? (T) this.find( object.getId() )
                                            : null;
    }

    public <T extends Entity> T find( Integer id ) throws ManagerException {
        try {
            return (T) this.getManager().find( this.getEntityClass(), id );
        } catch ( Throwable e ) {
            throw new ManagerException();
        }
    }

    public Class<? extends Entity> getEntityClass() {
        return this.entityClass;
    }

    public <T extends Entity> List<T> findBy( String name, Object value ) throws ManagerException {
        try {
            return (List<T>) this.executeQuery( this.buildMatchesQuery( new String[] { name }, new Object[] { value }, new String[] { "=" } ) );
        } catch ( Throwable e ) {
            throw new ManagerException();
        }
    }

    public Entity getOrCreateBy( String name, Object value ) throws ManagerException {
        try {
            Entity object = this.findOneBy(name, value);
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

    public boolean isExists( Entity object ) throws ManagerException {
        try {
            return null != this.find( object.getId() );
        } catch ( Throwable e ) {
            return false;
        }
    }

    public <T extends Entity> T findOneBy( String name, Object value ) throws ManagerException {
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

    public List<? extends Entity> findAll() throws ManagerException {
        return this.getManager().createQuery("select t from " + this.getEntityName() + " t" ).getResultList();
    }

    public List<? extends Entity> whereIn( List<Integer> ids ) throws ManagerException {
        return this.whereIn( ids.toArray( new Integer[ids.size()] ) );
    }

    public List<? extends Entity> whereIn( Set<Integer> ids ) throws ManagerException {
        return this.whereIn( ids.toArray( new Integer[ids.size()]));
    }

    public List<? extends Entity> whereIn( Integer[] ids ) throws ManagerException {
        /**
         * @TODO необходимо получать имя идентификатора для конкретной сущности, ибо
         * он может быть различным
         */
        return this.whereIn( "id", ids );
    }

    

    public List<? extends Entity> whereIn( String name, Integer[] ids ) throws ManagerException {
        return this.getManager().createQuery("from " + this.getEntityName() + " where " + name + " in (:ids)")
                                    .setParameter("ids", ids )
                                        .getResultList();


    }

    public List<Integer> getIdsBy( String name, Object value ) throws ManagerException {
        return this.getManager().createQuery(
            this.buildMatchesQuery(new String[] {name}, new Object[] {value}, new String[] {"="}, new String[] { "id" } )
        ).getResultList();
    }

    public List<Integer> getIds( Integer count ) throws ManagerException {
        return this.getIds(0, count);
    }

    public List<Integer> getIds( Integer from, Integer count ) throws ManagerException {
        return this.getManager().createQuery(
            this.buildMatchesQuery( new String[] {}, new Object[] {}, new String[] {}, new String[] { "id" }, from, count )
        ).getResultList();
    }

    @Deprecated
    public <T extends Entity> List<T> findMatches( Map<String, Object> constrain ) throws ManagerException {
        String[] operators = new String[ constrain.size() ];
        Arrays.fill( operators, "=" );
        
        return this.findMatches( constrain, operators  );
    }

    @Deprecated
    public <T extends Entity> List<T> findMatches( Map<String, Object> constrain, String[] operators ) throws ManagerException {
        return this.findMatches( constrain.keySet().toArray( new String[constrain.size()]), constrain.values().toArray( new Object[constrain.size()]), operators );
    }

    @Deprecated
    public <T extends Entity> List<T> findMatches( String[] names, Object[] values ) throws ManagerException {
        return this.findMatches( names, values, this.getDefaultOperators(values.length));
    }

    @Deprecated
    public <T extends Entity> List<T> findMatches( String[] names, Object[] values, String[] operators ) throws ManagerException {
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

    @Deprecated
    public <T extends Entity> T findOneMatched( String[] names, Object[] values ) throws ManagerException {
        return this.<T>findOneMatched( names, values, this.getDefaultOperators(values.length) );
    }

    @Deprecated
    public <T extends Entity> T findOneMatched( String[] names, Object[] values, String[] operators ) throws ManagerException {
        return this.<T>findOneMatched( names, values, operators, new String[] {} );
    }

    @Deprecated
    public <T extends Entity> T findOneMatched( String[] names, Object[] values, String[] operators, String[] select ) throws ManagerException {
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

    public static String getEntityName( Entity entity ) {
        return getEntityName( entity.getClass() );
    }

    public String getEntityName() {
        return getEntityName( this.getEntityClass() );
    }


    public static String getEntityName( Class<? extends Entity> clazz ) {
        javax.persistence.Entity annon = clazz.getAnnotation( javax.persistence.Entity.class );
        if ( annon != null ) {
            String result = annon.name();

            if ( !result.isEmpty() ) {
                return result;
            }
        }

        return clazz.getCanonicalName();
    }

    protected <T extends Entity> boolean isTargetEntity( T object ) {
        return this.isTargetEntity( object.getClass() );
    }

    protected <T extends Entity> boolean isTargetEntity( Class<T> object ) {
        return this.getEntityClass().equals( object );
    }

}