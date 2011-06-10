/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.persistence.dao.jpa;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.annotations.QueryHolder;
import com.redshape.persistence.dao.jpa.executors.CriteriaExecutor;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.IQueryHolder;
import com.redshape.persistence.dao.query.QueryBuilderException;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.entities.IEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.Table;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author user
 */
public abstract class AbstractJPADAO<T extends IEntity> implements IJPADAO<T>{
    private static final Logger log = Logger.getLogger(AbstractJPADAO.class);
    private Class<T> entityClass;
    
    private Map<Class<T>, com.redshape.persistence.dao.query.IQueryHolder> queryHolders = new HashMap<Class<T>, IQueryHolder>();
    
    public AbstractJPADAO( Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManagerFactory emFactory;
    @PersistenceContext( type = PersistenceContextType.EXTENDED, name = "persistenceManager" )
    protected EntityManager em;
    
    transient Object readLock = new Object();
    transient Object writeLock = new Object();
    
	@Override
    public void save( Collection<T> list ) throws DAOException {
        for ( T record : list ) {
            this.save(record, true );
        }
    }

	public void setEntityManagerFactory( EntityManagerFactory ef ) {
		this.emFactory = ef;
	}
	
	protected EntityManagerFactory getEntityManagerFactory() {
		return this.emFactory;
	}
	
	protected EntityManager getEntityManager() {
		if ( this.em == null ) {
			this.em = this.getEntityManagerFactory().createEntityManager();
		}
		
		return this.em;
	}
	
	protected void rollbackTransaction() {
		if ( !this.getEntityManager().getTransaction().isActive() ) {
			return;
		}
		
		this.getEntityManager().getTransaction().rollback();
	}
	
	protected void beginTransaction() {
		if ( this.getEntityManager().getTransaction().isActive() ) {
			return;
		}
		
		this.getEntityManager().getTransaction().begin();
	}
	
	protected void commitTransaction() {
		if ( !this.getEntityManager().getTransaction().isActive() ) {
			return;
		}
		
		this.getEntityManager().getTransaction().commit();
	}
	
	protected T save( T object, boolean isCollection ) throws DAOException {
		try {
			if ( !isCollection ) {
				this.beginTransaction();
			}
			
			object = this.getEntityManager().merge(object);
	        
	        return object;
		} catch ( Throwable e ) {
    		log.error( e.getMessage(), e );
			try {
				this.rollbackTransaction();
			} catch ( Throwable ex ) {}
			
			throw new DAOException( e.getMessage(), e );
		} finally {
			if ( !isCollection ) {
				try {
					this.commitTransaction();
				} catch ( Throwable e ) {
					this.rollbackTransaction();
				}
	        }
		}
	}
	
    @Override
    public T save(T object) throws DAOException {
        return this.save( object, false );
    }
    
    @Override
    synchronized public T update(T object) throws DAOException {
    	this.getEntityManager().refresh(object);
        return object;
    }

    @Override
    public void update(Collection<T> list ) throws DAOException {
        for ( T item : list ) {
            this.update( item );
        }
    }
    
    @Override
    public void persist( T record ) throws DAOException {
    	this.getEntityManager().persist(record);
    }

    @Override
    public void removeAll() throws DAOException {
        this.executeUpdate("delete from " + this.getEntityName());
    }
    
    @Override
    public void remove( Collection<T> objects ) throws DAOException {
    	try {
	    	this.beginTransaction();
	    	
	    	for ( T record : objects ) {
	    		this.remove(record, true);
	    	}
    	} catch ( Throwable e ) {
    		log.error( e.getMessage(), e );
			try {
				this.rollbackTransaction();
			} catch ( Throwable ex ) {}
			
			throw new DAOException( e.getMessage(), e );
		} finally {
			try {
				this.commitTransaction();
			} catch ( Throwable e ) {
				this.rollbackTransaction();
			}
		}
    }

    @SuppressWarnings("unchecked")
	protected void remove( T object, boolean isCollection ) throws DAOException {
    	try {
    		if ( !isCollection ) {
    			this.beginTransaction();
    		}
    		
    		this.getEntityManager().remove(object);
    			
    		
        } catch (Throwable e) {
    		log.error( e.getMessage(), e );
			try {
				this.rollbackTransaction();
			} catch ( Throwable ex ) {}
			
			throw new DAOException( e.getMessage(), e );
        } finally {
        	if ( !isCollection ) {
        		try {
					this.commitTransaction();
				} catch ( Throwable e ) {
					this.rollbackTransaction();
				}
    		}
		}
    }
    
	@Override
    public void remove(T object) throws DAOException {
        this.remove(object, false);
    }

    protected void executeUpdate(final String query) throws DAOException {
        this.executeUpdate(query, new HashMap<String, Object>());
    }

    protected void executeUpdate(final String queryString, final Map<String, Object> params) throws DAOException {
		Query query = this.getEntityManager().createQuery(queryString);
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        query.executeUpdate();
    }

    @Override
    public T findById( final Long id ) throws DAOException {
        try {
            return this.getEntityManager().find( this.getEntityClass(), id );
        } catch ( Throwable e ) {
            throw new DAOException( e.getMessage(), e );
        }
    }

    
    @Override
    public List<T> executeQuery( IQuery query ) throws DAOException {
        return this.executeQuery(query, new HashMap<String, Object>() );
    }

    @Override
    public List<T> executeQuery( IQuery query, Map<String, Object> parameters ) throws DAOException {
        return this.executeQuery( query, parameters, -1, -1 );
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<T> executeQuery( final IQuery query, 
    							 final Map<String, Object> parameters, 
    							 final int offset, 
    							 final int limit ) throws DAOException {
		if ( parameters != null ) {
			for ( String key : parameters.keySet() ) {
				query.setAttribute( key, parameters.get(key) );
			}
		}

        CriteriaExecutor executor = new CriteriaExecutor( this.getEntityManager(), query);
        
        try {
	        Query jpaQuery = executor.execute();
	        if ( offset > 0 ) {
	        	jpaQuery.setFirstResult( offset );
	        }
	        
	        if ( limit > 0 ) {
	        	jpaQuery.setMaxResults(limit);
	        }
	        
	        return  jpaQuery.getResultList();
        } catch ( QueryExecutorException e ) {
        	throw new DAOException( "Query execution failed", e );
        }
    }

    @Override
    public List<T> executeNamedQuery(String queryName, Map<String, Object> params) throws DAOException {
        return this.executeNamedQuery(queryName, params, -1);
    }

    @Override
    public List<T> executeNamedQuery(String queryName, Map<String, Object> params, int limit) throws DAOException {
        return this.executeNamedQuery( queryName, params, limit, -1 );
    }

    @Override
    public List<T> executeNamedQuery( final String queryName, final Map<String, Object> params, final int limit, final int offset ) throws DAOException {
        IQueryHolder queryHolder = this.getQueryHolder();
        if ( queryHolder != null && queryHolder.isQueryExists(queryName) ) {
            try {
                return this.executeQuery( queryHolder.findQuery(queryName), params, limit, offset );
            } catch ( QueryBuilderException e ) {
                throw new DAOException( e.getMessage(), e );
            }
        }

		Query query = this.getEntityManager().createNamedQuery(queryName);
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        if (limit > 0 ) {
            query.setMaxResults(limit);
        }
        
        if ( offset > 0 ) {
        	query.setFirstResult(offset);
        }
        
        return query.getResultList();
    }

    public IQueryHolder getQueryHolder() throws DAOException {
    	IQueryHolder concreteHolder = this.queryHolders.get( this.getEntityClass() );
    	if ( concreteHolder != null ) {
    		return concreteHolder;
    	}

	    QueryHolder holder = this.getEntityClass().getAnnotation(QueryHolder.class);
	    if ( holder == null ) {
		    return null;
	    }

	    Class<? extends IQueryHolder> value = holder.value();
        try {
            concreteHolder = value.newInstance();
            concreteHolder.init();
            this.queryHolders.put( this.getEntityClass(), concreteHolder );
        } catch (Throwable e) {
        	log.error( e.getMessage(), e );
            throw new DAOException(e.getMessage(), e);
        }

        return concreteHolder;
    }

    protected Class<T> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public List<T> findAll() throws DAOException {
        return this.findAll( -1, -1 );
    }

    @Override
    public List<T> findAll( int offset ) throws DAOException {
        return this.findAll(offset, -1);
    }

    @Override
    public List<T> findAll( final int offset, final int count ) throws DAOException {
    	Query query = this.getEntityManager().createQuery( String.format("from %s", getEntityName() ) );

        if ( offset > 0 ) {
            query.setFirstResult( offset );
        }

        if ( count > 0 ) {
            query.setMaxResults(count);
        }

        return query.getResultList();
	}

    public static String getEntityName(IEntity entity) {
        return getEntityName(entity.getClass());
    }

    public String getEntityName() {
        return getEntityName(this.getEntityClass());
    }

    public Long count() throws DAOException {
		return (Long) this.getEntityManager().createQuery("select count(*) from " + getEntityName() )
				 	    .getSingleResult();
    }

    public static String getEntityName(Class<? extends IEntity> clazz) {
        javax.persistence.Entity annon = clazz.getAnnotation(javax.persistence.Entity.class);
        String result = clazz.getCanonicalName();
        if (annon != null && annon.name() != null && !annon.name().isEmpty()) {
            result = annon.name();
        } else {
            javax.persistence.Table tableAnnon = clazz.getAnnotation(Table.class);
            if (tableAnnon != null && tableAnnon.name() != null && !tableAnnon.name().isEmpty()) {
                result = tableAnnon.name();
            }
        }

        return result;
    }

}
