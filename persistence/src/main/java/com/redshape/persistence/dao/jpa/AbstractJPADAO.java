package com.redshape.persistence.dao.jpa;

import com.redshape.persistence.dao.AbstractDao;
import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.IDAO;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.IQueryBuilder;
import com.redshape.persistence.dao.query.executors.IExecutionRequest;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;
import com.redshape.utils.Function;
import com.redshape.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractJPADAO<T extends IEntity> extends AbstractDao<T> implements IDAO<T> {

    protected abstract class ExecutorRequest<Z> implements IExecutionRequest<Z> {
        private int offset;
        private int limit;
        
        protected int offset() {
            return this.offset;
        }
        
        @Override
        public IExecutionRequest offset(int from) {
            this.offset = from;
            return this;
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

    protected AbstractJPADAO(Class<T> entityClass, IQueryExecutorService executor, IQueryBuilder builder) {
        super(entityClass);

        this.service = executor;
        this.builder = builder;
    }

    public void setService(IQueryExecutorService service) {
        this.service = service;
    }

    public IQueryExecutorService getService() {
        return service;
    }

    public IQueryBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(IQueryBuilder builder) {
        this.builder = builder;
    }

    protected IExecutionRequest<List<T>> execute( final IQuery query ) throws DAOException {
        return new ExecutorRequest<List<T>>() {
            @Override
            public List<T> execute() throws DAOException {
                query.setOffset( Commons.select(query.getOffset(), this.offset()) );
                query.setLimit( Commons.select(query.getLimit(), this.limit()) );
                
                return (List<T>) AbstractJPADAO.this.service.execute(query);
            }
        };
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
                                StringUtils.join(object, ",", new Function<Object, String>() {
                                    @Override
                                    public String invoke(Object... arguments)
                                            throws InvocationTargetException {
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
        return this.service.execute(
            this.getBuilder()
                .query( this.getEntityClass() )
                .select(
                    this.getBuilder().function("count", this.getBuilder().reference("id"))
                )
        ).getSingleValue();
    }



    @Override
    public T findById(Long id) throws DAOException {
        IQuery query = this.getBuilder().query(AbstractJPADAO.this.getEntityClass());
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
    public IExecutionRequest<List<T>> findAll() throws DAOException {
        final IQuery query = this.getBuilder().query(this.getEntityClass());

        return new ExecutorRequest<List<T>>() {
            @Override
            public List<T> execute() throws DAOException {
                query.setOffset( this.offset() );
                query.setLimit( this.limit() );

                return AbstractJPADAO.this.getService()
                        .<T>execute(query)
                        .getResultsList();
            }
        };
    }
}
