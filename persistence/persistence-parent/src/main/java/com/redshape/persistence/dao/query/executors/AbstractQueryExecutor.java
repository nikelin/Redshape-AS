/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.expressions.*;
import com.redshape.persistence.dao.query.expressions.operations.BinaryOperation;
import com.redshape.persistence.dao.query.expressions.operations.UnaryOperation;
import com.redshape.persistence.dao.query.statements.*;
import com.redshape.persistence.entities.IEntity;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

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

    public AbstractQueryExecutor(IQuery<? extends IEntity> query) {
        this.query = query;
    }

    protected IQuery<? extends IEntity> getQuery() {
        return this.query;
    }

    @Override
    public T execute() throws QueryExecutorException {
        P predicate = null;
        if ( !this.getQuery().joins().isEmpty() ) {
            for (IJoinStatement statement : this.getQuery().joins() ) {
                this.processStatement( statement );
            }
        }

        if ( !this.getQuery().aliases().isEmpty() ) {
            for ( IAliasStatement statement : this.getQuery().aliases() ) {
                if ( !statement.doEvaluateSource() ) {
                    continue;
                }

                this.processStatement(statement);
            }
        }

        if ( this.getQuery().getExpression() != null ) {
            predicate = this.processExpression(this.getQuery().getExpression() );
        }
        
        return this.processResult( predicate );
    }

    abstract protected T processResult( P predicate ) throws QueryExecutorException;

    abstract public E processExpression( InExpression expression ) throws QueryExecutorException;
    
    abstract public E processExpression( LikeExpression expression ) throws QueryExecutorException;
    
    abstract public E processExpression( UnaryOperation operation ) throws QueryExecutorException;

    abstract public E processExpression( BinaryOperation operation ) throws QueryExecutorException;

    abstract public E processExpression( FunctionExpression expression ) throws QueryExecutorException;

    abstract public P processExpression(EqualsOperation expression) throws QueryExecutorException;

    abstract public P processExpression(LessThanOperation less) throws QueryExecutorException;

    abstract public P processExpression(GreaterThanOperation greater) throws QueryExecutorException;

    abstract public P processExpression(AndExpression and) throws QueryExecutorException;

    abstract public P processExpression(OrExpression or) throws QueryExecutorException;

    abstract public P processExpression(NotOperation not) throws QueryExecutorException;

    abstract public E processStatement(JoinStatement statement ) throws QueryExecutorException;

    abstract public E processStatement(ScalarStatement<?> scalar) throws QueryExecutorException;;

    abstract public E processStatement(ReferenceStatement reference) throws QueryExecutorException;;

    abstract public E processStatement(IAliasStatement statement) throws QueryExecutorException;

    @SuppressWarnings("unchecked")
	public <V extends E> V processStatement( IStatement statement ) throws QueryExecutorException {
        try {
            if ( statement instanceof IExpression ) {
                return (V) this.processExpression( (IExpression) statement );
            }

            return (V) this.getClass()
                       .getMethod("processStatement", statement.getClass())
                       .invoke(this, statement);
        } catch ( InvocationTargetException e ) {
        	throw new QueryExecutorException( e.getCause().getMessage(), e.getCause() );
        } catch (Throwable e) {
            throw new QueryExecutorException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
	public P processExpression(IExpression statement) throws QueryExecutorException {
        try {
        	Object expression = this.getClass()
	            .getMethod("processExpression", statement.getClass())
	            .invoke(this, statement);
        	if ( expression instanceof IExpression ) {
        		return this.processExpression( (IExpression) statement );
        	}
	            
        	return (P) expression;
        } catch ( InvocationTargetException e ) {
        	throw new QueryExecutorException( e.getCause().getMessage(), e.getCause() );
        } catch (Throwable e) {
            throw new QueryExecutorException(e.getMessage(), e);
        }
    }

}
