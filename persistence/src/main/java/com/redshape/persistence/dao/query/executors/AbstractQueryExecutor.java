package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.expressions.AndExpression;
import com.redshape.persistence.dao.query.expressions.EqualsOperation;
import com.redshape.persistence.dao.query.expressions.GreaterThanOperation;
import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.expressions.LessThanOperation;
import com.redshape.persistence.dao.query.expressions.NotOperation;
import com.redshape.persistence.dao.query.expressions.OrExpression;
import com.redshape.persistence.dao.query.statements.IStatement;
import com.redshape.persistence.dao.query.statements.ReferenceStatement;
import com.redshape.persistence.dao.query.statements.ScalarStatement;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: cwiz
 * Date: 24.11.10
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractQueryExecutor<T, P, E> implements IQueryExecutor<T> {
    private static final Logger log = Logger.getLogger( AbstractQueryExecutor.class );
    private IQuery query;

    public AbstractQueryExecutor(IQuery query) {
        this.query = query;
    }

    protected IQuery getQuery() {
        return this.query;
    }

    @Override
    public T execute() throws QueryExecutorException {
        return this.processResult( this.processExpression(this.getQuery().getExpression() ) );
    }

    abstract protected T processResult( P predicate ) throws QueryExecutorException;

    abstract public P processExpression(EqualsOperation expression) throws QueryExecutorException;

    abstract public P processExpression(LessThanOperation less) throws QueryExecutorException;

    abstract public P processExpression(GreaterThanOperation greater) throws QueryExecutorException;

    abstract public P processExpression(AndExpression and) throws QueryExecutorException;

    abstract public P processExpression(OrExpression or) throws QueryExecutorException;

    abstract public P processExpression(NotOperation not) throws QueryExecutorException;

    abstract public E processStatement(ScalarStatement<?> scalar) throws QueryExecutorException;;

    abstract public E processStatement(ReferenceStatement reference) throws QueryExecutorException;;

    public <V extends E> V processStatement( IStatement statement ) throws QueryExecutorException {
        try {
            return (V) this.getClass()
                       .getMethod("processStatement", statement.getClass())
                       .invoke(this, statement);
        } catch (Throwable e) {
            log.error( e.getMessage(), e );
            throw new QueryExecutorException(e.getMessage());
        }
    }

    public P processExpression(IExpression statement) throws QueryExecutorException {
        try {
        	Object expression = this.getClass()
	            .getMethod("processExpression", statement.getClass())
	            .invoke(this, statement);
        	if ( expression instanceof IExpression ) {
        		return this.processExpression( (IExpression) statement );
        	}
	            
        	return (P) expression;
        } catch (Throwable e) {
            log.error( e.getMessage(), e );
            throw new QueryExecutorException(e.getMessage());
        }
    }

}
