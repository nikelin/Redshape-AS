/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.persistence.dao.jpa.executors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.executors.AbstractQueryExecutor;
import com.redshape.persistence.dao.query.expressions.AndExpression;
import com.redshape.persistence.dao.query.expressions.EqualsOperation;
import com.redshape.persistence.dao.query.expressions.GreaterThanOperation;
import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.expressions.LessThanOperation;
import com.redshape.persistence.dao.query.expressions.NotOperation;
import com.redshape.persistence.dao.query.expressions.OrExpression;
import com.redshape.persistence.dao.query.statements.ReferenceStatement;
import com.redshape.persistence.dao.query.statements.ScalarStatement;
import com.redshape.persistence.entities.IEntity;

/**
 *
 * @author user
 */
public class CriteriaExecutor extends AbstractQueryExecutor<Query, Predicate, Expression<?>>   {
    private EntityManager manager;
    private CriteriaBuilder builder;
    private ThreadLocal<CriteriaQuery<IEntity>> criteria = new ThreadLocal<CriteriaQuery<IEntity>>();

    public CriteriaExecutor( EntityManager manager, IQuery query) {
        super(query);

        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
    }

    protected EntityManager getManager() {
        return this.manager;
    }

    protected CriteriaBuilder getBuilder() {
        return this.builder;
    }

    @Override
    public Query execute() throws QueryExecutorException {
        this.criteria.set( this.builder.createQuery( this.getQuery().getEntityClass() ) );
        
        return super.execute();
    }
    
    @Override
    protected Query processResult( Predicate expression  ) throws QueryExecutorException {
    	CriteriaQuery<IEntity> criteriaQuery = this.criteria.get();
    	Root<IEntity> root = criteriaQuery.from( this.getQuery().getEntityClass() );
    	criteriaQuery.select( root );
    	criteriaQuery.where( expression );

        Query nativeQuery = this.getManager().createQuery( criteriaQuery );
        for ( String key : this.getQuery().getAttributes().keySet() ) {
        	if ( !this.getQuery().hasAttribute( key ) ) {
        		throw new QueryExecutorException();
        	}
        	
        	nativeQuery.setParameter( key, this.getQuery().getAttribute( key ) );
        }
        
        return nativeQuery;
    }

    @Override
    public Expression<?> processStatement(ScalarStatement<?> scalar) throws QueryExecutorException {
    	// @TODO: split function of scalar into FieldStatement and LiteralStatement<>
    	if ( scalar.getValue() instanceof String ) {
    		return this.criteria.get().from( this.getQuery().getEntityClass() ).get( String.valueOf( scalar.getValue() ) );
    	} else {
    		return this.getBuilder().literal( scalar.getValue() );
    	}
    }

    @Override
    public Expression<?> processStatement(ReferenceStatement reference) throws QueryExecutorException {
    	if ( this.getQuery().hasAttribute( reference.getValue() ) ) {
    		return this.getBuilder().parameter( this.getQuery().getAttribute( reference.getValue() ).getClass(), reference.getValue() );
    	} else {
    		return this.criteria.get().from( this.getQuery().getEntityClass() ).get( String.valueOf( reference.getValue() ) );
    	}
    }
    
    @Override
    public Predicate processExpression(EqualsOperation expression) throws QueryExecutorException {
        return this.getBuilder().equal(
            this.processStatement( expression.getLeftOperand() ),
            this.processStatement( expression.getRightOperand() )
        );
    }

    @Override
    public Predicate processExpression(LessThanOperation less) throws QueryExecutorException {
        return this.getBuilder().lt(
            this.<Expression<Number>>processStatement( less.getLeftOperand() ),
            this.<Expression<Number>>processStatement( less.getRightOperand() )
        );
    }

    @Override
    public Predicate processExpression(GreaterThanOperation greater) throws QueryExecutorException {
        return this.getBuilder().gt(
            this.<Expression<Number>>processStatement( greater.getLeftOperand() ),
            this.<Expression<Number>>processStatement( greater.getRightOperand() )
        );
    }

    @Override
    public Predicate processExpression(AndExpression and) throws QueryExecutorException {
        Predicate[] expressions = new Predicate[ and.getTerms().length ];

        int i = 0;
        for ( IExpression part : and.getTerms() ) {
            expressions[i++] = this.processExpression( part );
        }

        return this.getBuilder().and(expressions);
    }

    @Override
    public Predicate processExpression(OrExpression or) throws QueryExecutorException {
    	Predicate[] expressions = new Predicate[ or.getTerms().length ];

        int i = 0;
        for ( IExpression part : or.getTerms() ) {
            expressions[i++] = this.processExpression( part );
        }

        return this.getBuilder().or( expressions );
    }

    @Override
    public Predicate processExpression(NotOperation not) throws QueryExecutorException {
        return this.getBuilder().not( this.processExpression( not.getExpression() ) );
    }


}
