package com.redshape.persistence.dao.jpa.executors.services;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.annotations.QueryHolder;
import com.redshape.persistence.dao.jpa.executors.CriteriaExecutor;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.IQueryHolder;
import com.redshape.persistence.dao.query.QueryBuilderException;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.executors.IExecutorResult;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class JPAExecutorService extends JpaDaoSupport implements IQueryExecutorService {
    private static final Logger log = Logger.getLogger(JPAExecutorService.class);
    
    public class ExecutionResult<T extends IEntity> implements IExecutorResult<T> {
        private List<? extends Object> results = new ArrayList<Object>();
        
        public ExecutionResult( Object object ) {
            if ( object == null ) {
                return;
            }
            
            if ( object instanceof Collection ) {
                this.results.addAll( (Collection) object );
            } else {
                this.results = Commons.list(object);
            }
        }
        
        public ExecutionResult(List<?> results) {
            this.results = (List<Object>) results;
        }

        @Override
        public <Z> List<Z> getValuesList() {
            return (List<Z>) this.results;
        }

        @Override
        public <Z> Z getSingleValue() {
            return (Z) Commons.firstOrNull(this.results);
        }

        @Override
        public List<T> getResultsList() {
            return (List<T>) this.results;
        }

        @Override
        public T getSingleResult() {
            return (T) Commons.firstOrNull( this.results );
        }

        @Override
        public int count() {
            return this.results.size();
        }
    }
    
    @PersistenceContext( name = "persistenceManager", type = PersistenceContextType.TRANSACTION )
    protected EntityManager em;

    private Map<Class<? extends IEntity>, IQueryHolder> queryHolders = new HashMap<Class<? extends IEntity>, IQueryHolder>();

    public JPAExecutorService() {
        this( new HashMap<Class<? extends IEntity>, IQueryHolder>() );
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public JPAExecutorService(Map<Class<? extends IEntity>, IQueryHolder> queryHolders) {
        super();

        this.queryHolders = queryHolders;
    }

    @Override
    @Transactional
    public <T extends IEntity> IExecutorResult<T> execute(IQuery query) throws DAOException {
        Object value = null;
        if ( this.isUpdateQuery(query) ) {
            value = this.executeUpdate(query);
        } else if ( query.isNative() ) {
            value = this.executeNamedQuery( query.getEntityClass(), query.getName(), query.getAttributes() );
        } else if ( query.isCount() ) {
            value = this.executeCountQuery(query);
        } else {
            value = this.executeSelect(query);
        }
        
        return new ExecutionResult<T>(value);
    }
    
    protected int executeCountQuery( IQuery query ) throws DAOException {
        return this.em.createNativeQuery("select count(*) from " + this.getEntityName(query) )
                    .getFirstResult();
    }

    protected <T extends IEntity> List<T> executeSelect( IQuery query ) throws DAOException {
        CriteriaExecutor executor = new CriteriaExecutor( this.em, query);

        try {
            Query jpaQuery = executor.execute();
            if ( query.getOffset() > 0 ) {
                jpaQuery.setFirstResult( query.getOffset() );
            }

            if ( query.getLimit() > 0 ) {
                jpaQuery.setMaxResults( query.getLimit() );
            }

            try {
                return  jpaQuery.getResultList();
            } catch ( EntityNotFoundException e ) {
                return new ArrayList<T>();
            }
        } catch ( QueryExecutorException e ) {
            throw new DAOException( "Query execution failed", e );
        }
    }

    @Transactional
    protected <T extends IEntity> T executeUpdate(IQuery query) throws DAOException {
        T result = null;
        if ( query.isRemove() ) {
            if ( query.entity() != null ) {
                result = this.executeRemove(query);
            } else {
                result = this.executeRemoveAll(query);
            }
        } else if ( query.isUpdate() || query.isCreate() ) {
            result = this.executeSave(query);
        }

        return result;
    }

    @Transactional
    protected <T extends IEntity> T executeSave( IQuery query ) throws DAOException {
        return (T) this.em.merge( query.entity() );
    }
    
    @Transactional
    protected <T extends IEntity> T executeRemoveAll( IQuery query ) throws DAOException {
        this.em.createQuery("delete from " + this.getEntityName( query ) )
                .executeUpdate();
        return null;
    }

    @Transactional
    protected <T extends IEntity> T executeRemove( IQuery query ) throws DAOException {
        IEntity object = query.entity();
        if ( !this.em.contains( object ) ) {
            IEntity objInDb = (IEntity) this.em.find( object.getClass(), object.getId() );
            BeanUtils.copyProperties(object, objInDb);
            object = objInDb;
        }

        this.em.remove(object);

        return (T) object;
    }
    
    protected boolean isUpdateQuery( IQuery query ) {
        return query.isCreate() || query.isUpdate()
                || query.isRemove();
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                          String queryName,
                                                          Map<String, Object> params )
        throws DAOException {
        return this.executeNamedQuery(entityClazz, queryName, params, -1 );
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                          String queryName,
                                                          Map<String, Object> params,
                                                          int offset )
        throws DAOException {
        return this.executeNamedQuery(entityClazz, queryName, params, offset, -1 );
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                          String queryName,
                                                          Map<String, Object> params,
                                                          int offset,
                                                          int limit )
        throws DAOException {
        IQueryHolder queryHolder = this.getQueryHolder(entityClazz);
        if ( queryHolder != null && queryHolder.isQueryExists(queryName) ) {
            try {
                IQuery query = queryHolder.findQuery(queryName).duplicate();
                query.setOffset(offset);
                query.setLimit(limit);
                query.setAttributes(params);
                
                return this.<T>execute( query );
            } catch ( QueryBuilderException e ) {
                throw new DAOException( e.getMessage(), e );
            }
        }

        Query query = this.em.createNamedQuery(queryName);
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        if (limit > 0 ) {
            query.setMaxResults(limit);
        }

        if ( offset > 0 ) {
            query.setFirstResult(offset);
        }

        return new ExecutionResult<T>( query.getResultList() );
    }

    protected IQueryHolder getQueryHolder( Class<? extends IEntity> entityClazz ) throws DAOException {
        IQueryHolder concreteHolder = this.queryHolders.get( entityClazz );
        if ( concreteHolder != null ) {
            return concreteHolder;
        }

        QueryHolder holder = entityClazz.getAnnotation(QueryHolder.class);
        if ( holder == null ) {
            return null;
        }

        Class<? extends IQueryHolder> value = holder.value();
        try {
            concreteHolder = value.newInstance();
            concreteHolder.init();
            this.queryHolders.put( entityClazz, concreteHolder );
        } catch (Throwable e) {
            log.error( e.getMessage(), e );
            throw new DAOException(e.getMessage(), e);
        }

        return concreteHolder;
    }

    protected String getEntityName( IQuery query ) {
        Class<? extends IEntity> clazz = query.getEntityClass();
        javax.persistence.Entity annon = clazz.getAnnotation(javax.persistence.Entity.class);
        String result = clazz.getCanonicalName();
        if (annon != null && annon.name() != null && !annon.name().isEmpty()) {
            result = annon.name();
        } else {
            javax.persistence.Table tableMeta = clazz.getAnnotation(Table.class);
            if (tableMeta != null && tableMeta.name() != null && !tableMeta.name().isEmpty()) {
                result = tableMeta.name();
            }
        }

        return result;
    }

}
