package com.redshape.persistence.dao;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.IQueryBuilder;
import com.redshape.persistence.dao.query.executors.IExecutionRequest;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;
import com.redshape.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @param <T>
 * @author Cyril A. Karpenko <self@nikelin.ru>
 */
public class AbstractDao<T extends IEntity> implements IDAO<T> {

    protected class ExecutorRequest<Z extends IEntity> implements IExecutionRequest<Z> {
        private int offset;
        private int limit;
        
        private IQueryExecutorService service;
        private IQuery query;
        
        public ExecutorRequest( IQueryExecutorService service, IQuery query ) {
            this.service = service;
            this.query = query;
        }

        public List<Z> list() throws DAOException {
            query.setOffset( Commons.select(query.getOffset(), this.offset()) );
            query.setLimit( Commons.select(query.getLimit(), this.limit()) );

            return (List<Z>) AbstractDao.this.service.execute(query).getResultsList();
        }

        protected int offset() {
            return this.offset;
        }

        @Override
        public IExecutionRequest offset(int from) {
            this.offset = from;
            return this;
        }

        @Override
        public Z result() throws DAOException {
            return Commons.firstOrNull( this.list() );
        }

        @Override
        public <D> D resultValue() throws DAOException {
            return (D) Commons.firstOrNull( this.list() );
        }

        @Override
        public <Z> List<Z> listValue() throws DAOException {
            return (List<Z>) this.list();
        }

        protected int limit() {
            return this.limit;
        }

        @Override
        public IExecutionRequest limit(int count) {
            this.limit = count;
            return this;
        }
    }

    private IQueryBuilder builder;

    private IQueryExecutorService service;

    protected Class<? extends T> entityClass;

    protected AbstractDao(Class<? extends T> entityClass, IQueryExecutorService executor, IQueryBuilder builder) {
        this.entityClass = entityClass;
        this.service = executor;
        this.builder = builder;
        this.checkFields();
    }

    protected void checkFields() {
        if ( this.entityClass == null ) {
            throw new IllegalStateException("<null>: target entity class must be provided");
        }

        if ( this.service == null ) {
            throw new IllegalStateException("<null>: queries execution service must be provided");
        }

        if ( this.builder == null ) {
            throw new IllegalStateException("<null>: queries builder instance must be provided");
        }
    }

    @Override
    public Class<? extends T> getEntityClass() {
        return this.entityClass;
    }

    protected IQueryExecutorService getService() {
        return this.service;
    }

    protected IQueryBuilder getBuilder() {
        return builder;
    }

    @Transactional
    protected IExecutionRequest<T> execute( final IQuery query ) throws DAOException {
        return new ExecutorRequest<T>(this.service, query);
    }

    @Override
    public T save(T object) throws DAOException {
        return this.service.<T>execute(
                this.getBuilder()
                        .updateQuery(this.getEntityClass())
                        .entity(object)
        )
                .getSingleResult();
    }

    @Override
    public void save(Collection<T> object) throws DAOException {
        for ( T record : object ) {
            this.save(record);
        }
    }

    @Override
    @Transactional
    public void removeAll() throws DAOException {
        this.service.execute(this.getBuilder().removeQuery(this.getEntityClass()));
    }

    @Override
    public void remove(T object) throws DAOException {
        this.service.execute(
                this.getBuilder().removeQuery(this.getEntityClass())
                        .where(
                                this.getBuilder().equals(
                                        this.getBuilder().reference("id"),
                                        this.getBuilder().scalar(object.getId())
                                )
                        )
        );
    }

    @Override
    public void remove(Collection<T> object) throws DAOException {
        this.service.execute(
                this.getBuilder().removeQuery(this.getEntityClass())
                        .where(
                                this.getBuilder().in(
                                        this.getBuilder().reference("id"),
                                        this.getBuilder().array(
                                                this.getBuilder().scalar(
                                                        StringUtils.join(object, ",", new Lambda<String>() {
                                                            @Override
                                                            public String invoke(Object... arguments)
                                                                    throws InvocationException {
                                                                return ((T) arguments[0]).getId() == null ?
                                                                        "-1" : ((T) arguments[0]).getId().toString();
                                                            }
                                                        }).split(",")
                                                )
                                        )
                                )
                        )
        );
    }

    @Override
    public Long count() throws DAOException {
        return Long.valueOf(
            this.service.execute(
                this.getBuilder()
                        .countQuery( this.getEntityClass() )
            ).<Integer>getSingleValue()
        );
    }



    @Override
    public T findById(Long id) throws DAOException {
        IQuery query = this.getBuilder().query(AbstractDao.this.getEntityClass());
        query.where(
                this.getBuilder().equals(
                        this.getBuilder().reference("id"),
                        this.getBuilder().scalar(id)
                )
        );

        return this.service.<T>execute(query)
                .getSingleResult();
    }

    @Override
    public IExecutionRequest<T> findAll() throws DAOException {
        return new ExecutorRequest<T>(
            this.getService(),
            this.getBuilder().query(this.getEntityClass())
        );
    }
}
