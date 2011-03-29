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
import com.redshape.persistence.entities.IEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Table;
import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
public abstract class AbstractJPADAO<T extends IEntity> extends JpaDaoSupport implements IJPADAO<T>{
    private static final Logger log = Logger.getLogger(AbstractJPADAO.class);
    private Class<T> entityClass;
    private Map<Class<T>, com.redshape.persistence.dao.query.IQueryHolder> queryHolders = new HashMap<Class<T>, IQueryHolder>();
    
    public AbstractJPADAO( Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    

    @Override
    @Transactional
    /** @TODO: rewrite with native Hibernate batchUpdate **/
    public void save( Collection<T> list ) throws DAOException {
        for ( T record : list ) {
            this.save(record);
        }
    }

    @Override
    @Transactional
    public void save(T object) throws DAOException {
        this.getJpaTemplate().merge(object);
        this.getJpaTemplate().flush();
    }

    @Override
    @Transactional
    public void update(T object) throws DAOException {
        this.getJpaTemplate().merge( object );
    }

    @Override
    @Transactional
    public void update(Collection<T> list ) throws DAOException {
        for ( T item : list ) {
            this.update( item );
        }
    }

    @Override
    @Transactional
    public void removeAll() throws DAOException {
        this.executeUpdate("delete from " + this.getEntityName());
    }

    @Override
    @Transactional
    public void remove(IEntity object) throws DAOException {
        try {
            this.getJpaTemplate().remove(object);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new DAOException(e.getMessage());
        }
    }

    @Transactional
    protected void executeUpdate(final String query) throws DAOException {
        this.executeUpdate(query, new HashMap<String, Object>());
    }

    @Transactional
    protected void executeUpdate(final String queryString, final Map<String, Object> params) throws DAOException {
        this.getJpaTemplate().execute(
    		new JpaCallback<Integer>() {
    			public Integer doInJpa( EntityManager em ) {
    				Query query = em.createQuery(queryString);
    		        for (String key : params.keySet()) {
    		            query.setParameter(key, params.get(key));
    		        }

    		        return query.executeUpdate();
    			}
			}
        );
    	
    }

    @Override
    public T findById( final Long id ) throws DAOException {
        try {
            return this.getJpaTemplate().execute(
            		new JpaCallback<T>() {
            			public T doInJpa( EntityManager em ) {
            					return (T) em.createQuery( "from " + getEntityName( AbstractJPADAO.this.getEntityClass() ) + " where id=" + id )
                                      .getSingleResult();
            			}
            		}
    		);
        } catch ( Throwable e ) {
            throw new DAOException();
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

    @Override
    public List<T> executeQuery( final IQuery query, final Map<String, Object> parameters, final int offset, final int limit ) throws DAOException {
    	return this.getJpaTemplate().execute(
			new JpaCallback<List<T>>() {
				public List<T> doInJpa(EntityManager em) throws PersistenceException {
					try {
						if ( parameters != null ) {
							for ( String key : parameters.keySet() ) {
								query.setAttribute( key, parameters.get(key) );
							}
						}

    		            CriteriaExecutor executor = new CriteriaExecutor( em, query);
    		            
    		            Query query = executor.execute();
    		            if ( offset > 0 ) {
    		            	query.setFirstResult( offset );
    		            }
    		            
    		            if ( limit > 0 ) {
    		            	query.setMaxResults(limit);
    		            }
    		            
    		            return  query.getResultList();
					} catch ( Throwable e ) {
						log.error( e.getMessage(), e );
						throw new PersistenceException(e);
					}
				}
			}
		);  
    }

    @Override
    public List<T> executeNamedQuery(String queryName, Map<String, Object> params) throws DAOException {
        return this.executeNamedQuery(queryName, params, -1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> executeNamedQuery(String queryName, Map<String, Object> params, int limit) throws DAOException {
        return this.executeNamedQuery( queryName, params, limit, -1 );
    }

    @Override
    public List<T> executeNamedQuery( final String queryName, final Map<String, Object> params, final int limit, final int offset ) throws DAOException {
        IQueryHolder queryHolder = this.getQueryHolder();
        if ( queryHolder.isQueryExists(queryName) ) {
            try {
                return this.executeQuery( queryHolder.findQuery(queryName), params, limit, offset );
            } catch ( QueryBuilderException e ) {
                throw new DAOException( e.getMessage(), e );
            }
        }

        return this.getJpaTemplate().execute( new JpaCallback<List<T>>() {
        	public List<T> doInJpa( EntityManager em ) {
        		try {
	        		Query query = em.createNamedQuery(queryName);
	                for (String key : params.keySet()) {
	                    query.setParameter(key, params.get(key));
	                }
	
	                if (limit > 0 ) {
	                    query.setMaxResults(limit);
	                }
	
	                return query.getResultList();
        		} catch ( Throwable e ) {
        			log.error( e.getMessage(), e );
        			throw new PersistenceException( e.getMessage(), e );
        		}
        	}
		});
    }

    public IQueryHolder getQueryHolder() throws DAOException {
    	IQueryHolder concreteHolder = this.queryHolders.get( this.getEntityClass() );
    	if ( concreteHolder != null ) {
    		return concreteHolder;
    	}
    	
        Class<? extends IQueryHolder> value = this.getEntityClass()
                                                  .getAnnotation(QueryHolder.class)
                                                  .value();

        
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
    	return this.getJpaTemplate().execute(
			new JpaCallback<List<T>>() {
				public List<T> doInJpa( EntityManager em ) {
			        Query query = em.createQuery( String.format("from %s", getEntityName() ) );

			        if ( offset > 0 ) {
			            query.setFirstResult( offset );
			        }

			        if ( count > 0 ) {
			            query.setMaxResults(count);
			        }

			        return query.getResultList();
				}
			}
    	);
    }

    public static String getEntityName(IEntity entity) {
        return getEntityName(entity.getClass());
    }

    public String getEntityName() {
        return getEntityName(this.getEntityClass());
    }

    public Long count() throws DAOException {
    	return this.getJpaTemplate().execute( new JpaCallback<Long>() {
    		public Long doInJpa( EntityManager em ) {
    			return (Long) em.createQuery("select count(*) from " + getEntityName() )
    					 	    .getSingleResult();
    		}
		});
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
