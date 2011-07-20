package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.expressions.*;
import com.redshape.persistence.dao.query.expressions.operations.BinaryOperation;
import com.redshape.persistence.dao.query.expressions.operations.UnaryOperation;
import com.redshape.persistence.dao.query.statements.ReferenceStatement;
import com.redshape.persistence.dao.query.statements.ScalarStatement;
 
@SuppressWarnings("rawtypes")
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
    public Comparable processExpression(UnaryOperation operation) throws QueryExecutorException {
        throw new QueryExecutorException("Static executor not support operations currently");
    }

    @Override
    public Comparable processExpression(BinaryOperation operation) throws QueryExecutorException {
        throw new QueryExecutorException("Static executor not supports operations currently");
    }

    @Override
    public Boolean processExpression(FunctionExpression expression) throws QueryExecutorException {
        throw new QueryExecutorException("Static executor currently not support FunctionExpression object");
    }

    @SuppressWarnings("unchecked")
	@Override
    public Boolean processExpression(LessThanOperation less) throws QueryExecutorException {
        return this.processStatement(less.getLeftOperand() )
                .compareTo(
                    this.processStatement(
                        less.getRightOperand()
                    )
                ) == -1;
    }

    @SuppressWarnings("unchecked")
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
