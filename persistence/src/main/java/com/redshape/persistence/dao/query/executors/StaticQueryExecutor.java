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
import com.redshape.persistence.dao.query.statements.ReferenceStatement;
import com.redshape.persistence.dao.query.statements.ScalarStatement;

public class StaticQueryExecutor extends AbstractQueryExecutor<Boolean, Boolean, Comparable> 
									implements IStaticQueryExecutor<Boolean> {

    public StaticQueryExecutor(IQuery query) {
        super(query);
    }

    @Override
    public Boolean processExpression(EqualsOperation expression) throws QueryExecutorException {

        Object left = this.processStatement(expression.getLeftOperand());
        Object right = this.processStatement(expression.getRightOperand());

        return left.equals(right);
    }

    @Override
    protected Boolean processResult( Boolean predicate ) throws QueryExecutorException {
        return predicate;
    }

    @Override
    public Boolean processExpression(LessThanOperation less) throws QueryExecutorException {
        return this.processStatement(less.getLeftOperand() )
                .compareTo(
                    this.processStatement(
                        less.getRightOperand()
                    )
                ) == -1;
    }

    @Override
    public Boolean processExpression(GreaterThanOperation greater) throws QueryExecutorException {
        return this.processStatement(greater.getLeftOperand())
                .compareTo(
                    this.processStatement(
                        greater.getRightOperand()
                    )
                ) == 1;
    }
    
    @Override
    public Comparable<?> processStatement(ScalarStatement<?> scalar) {
        return (Comparable<?>) scalar.getValue() ;
    }

    @Override
    public Comparable<?> processStatement(ReferenceStatement reference) throws QueryExecutorException {
        if ( !this.getQuery().hasAttribute( reference.getValue() ) ) {
            throw new QueryExecutorException("Unknown attribute exception!");
        }

        return this.getQuery().getAttribute(reference.getValue());
    }

    @Override
    public Boolean processExpression(AndExpression and) throws QueryExecutorException {
        Boolean finalResult = null;

        IExpression[] terms = and.getTerms();
        for (IExpression term : terms) {
            if (finalResult == null) {
                finalResult = this.processExpression(term);
            } else {
                finalResult = finalResult && this.processExpression(term);
            }
        }

        return finalResult;
    }

    @Override
    public Boolean processExpression(OrExpression or) throws QueryExecutorException {
        Boolean finalResult = null;

        IExpression[] terms = or.getTerms();
        for (IExpression term : terms) {
            if (finalResult == null) {
                finalResult = this.processExpression(term);
            } else {
                finalResult = finalResult || this.processExpression(term);
            }
        }

        return finalResult;
    }

    @Override
    public Boolean processExpression(NotOperation not) throws QueryExecutorException {
        return !this.processExpression(not.getExpression());
    }

}
