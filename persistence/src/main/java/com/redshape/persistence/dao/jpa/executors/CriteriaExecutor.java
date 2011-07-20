/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.persistence.dao.jpa.executors;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.executors.AbstractQueryExecutor;
import com.redshape.persistence.dao.query.expressions.*;
import com.redshape.persistence.dao.query.expressions.operations.BinaryOperation;
import com.redshape.persistence.dao.query.expressions.operations.UnaryOperation;
import com.redshape.persistence.dao.query.statements.IStatement;
import com.redshape.persistence.dao.query.statements.ReferenceStatement;
import com.redshape.persistence.dao.query.statements.ScalarStatement;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;

/**
 *
 * @author user
 */
public class CriteriaExecutor extends AbstractQueryExecutor<Query, Predicate, Expression<?>>   {
    private EntityManager manager;
    private CriteriaBuilder builder;
    private CriteriaQuery<IEntity> criteria;
    private Root<?> root;

    public CriteriaExecutor( EntityManager manager, IQuery query) {
        super(query);

        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
        this.criteria = this.getBuilder().createQuery( this.getQuery().getEntityClass() );
        this.root = this.criteria.from( query.<IEntity>getEntityClass() );
        this.builder = manager.getCriteriaBuilder();
    }

    protected EntityManager getManager() {
        return this.manager;
    }

    protected CriteriaBuilder getBuilder() {
        return this.builder;
    }

    protected CriteriaQuery<IEntity> getCriteria() {
        return this.criteria;
    }

    @Override
    protected Query processResult( Predicate expression  ) throws QueryExecutorException {
    	this.getCriteria().where( expression );

        Query nativeQuery = this.getManager().createQuery( this.getCriteria() );
        for ( String key : this.getQuery().getAttributes().keySet() ) {
        	if ( !this.getQuery().hasAttribute( key ) ) {
        		throw new QueryExecutorException();
        	}
        	
        	nativeQuery.setParameter( key, this.getQuery().getAttribute( key ) );
        }

        return nativeQuery;
    }

    @Override
    public Expression<?> processExpression(UnaryOperation operation) throws QueryExecutorException {
        switch ( operation.getType() ) {
            case NEGATE:
                return this.getBuilder().neg(
                        (Expression<Number>) this.processStatement( operation.getTerm() ) );
            default:
                throw new QueryExecutorException("Unsupported unary operation "
                        + Commons.select( operation.getType(), "<null>" ) );
        }
    }

    @Override
    public Expression<?> processExpression(BinaryOperation operation) throws QueryExecutorException {
        switch ( operation.getType() ) {
            case SUM:
                return this.getBuilder().sum(
                    (Expression<Number>) this.processStatement( operation.getLeft() ),
                    (Expression<Number>) this.processStatement( operation.getRight() )
                );
            case SUBTRACT:
                return this.getBuilder().diff(
                    (Expression<Number>) this.processStatement( operation.getLeft() ),
                    (Expression<Number>) this.processStatement( operation.getRight() )
                );
            case DIVIDE:
                return this.getBuilder().quot(
                    (Expression<Number>) this.processStatement( operation.getLeft() ),
                    (Expression<Number>) this.processStatement( operation.getRight() )
                );
            case MOD:
                return this.getBuilder().mod(
                    (Expression<Integer>) this.processStatement( operation.getLeft() ),
                    (Expression<Integer>) this.processStatement( operation.getRight() )
                );
            case PROD:
                return this.getBuilder().prod(
                    (Expression<Number>) this.processStatement( operation.getLeft() ),
                    (Expression<Number>) this.processStatement( operation.getRight() )
                );
            default:
                throw new QueryExecutorException("Unsupported binary operation "
                        + Commons.select( operation.getType(), "<null>") );
        }
    }

    @Override
    public Expression<?> processExpression(FunctionExpression expression) throws QueryExecutorException {
        Expression[] expressions = new Expression[expression.getTerms().length];
        int i = 0;
        for ( IStatement term : expression.getTerms() ) {
            expressions[i++] = this.processStatement(term);
        }

        Expression<?> result =  this.getBuilder().function(
            expression.getName(),
            Object.class,
            expressions
        );

        return result;
    }

    @Override
    public Expression<?> processStatement(ScalarStatement<?> scalar) throws QueryExecutorException {
        Object value = scalar.getValue();
        if ( value instanceof IEntity ) {
            throw new QueryExecutorException("Not supported. Use dot-based (user.id) path notation");
        }

        if ( value != null ) {
            return this.getBuilder().literal( value );
        } else {
            return this.getBuilder().nullLiteral( Object.class );
        }
    }

    @Override
    public Expression<?> processStatement(ReferenceStatement reference) throws QueryExecutorException {
    	String path = reference.getValue();
        if ( path == null ) {
            return this.getBuilder().nullLiteral(Object.class);
        }

        String[] parts = path.toString().split("\\.");
        int offset = 0;
        Path<?> pathContext = this.root;
        while ( offset < parts.length ) {
            pathContext = pathContext.get(parts[offset++]);
        }

        return pathContext;
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
