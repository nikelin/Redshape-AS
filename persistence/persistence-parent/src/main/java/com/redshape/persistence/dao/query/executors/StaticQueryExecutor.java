package com.redshape.persistence.dao.query.executors;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.QueryExecutorException;
import com.redshape.persistence.dao.query.expressions.*;
import com.redshape.persistence.dao.query.expressions.operations.BinaryOperation;
import com.redshape.persistence.dao.query.expressions.operations.UnaryOperation;
import com.redshape.persistence.dao.query.statements.IArrayStatement;
import com.redshape.persistence.dao.query.statements.JoinStatement;
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
    public Comparable processStatement(JoinStatement statement) throws QueryExecutorException {
        throw new QueryExecutorException("Static executor not support join statements currently");
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
    public Comparable processExpression(InExpression expression) throws QueryExecutorException {
        Comparable source = this.processStatement(expression.getField());
        for ( Comparable value : this.processStatement(expression.getRange()) ) {
            if ( value.compareTo(source) == 0 ) {
                return true;
            }
        }

        return false;
    }

    @Override
    /**
     * @TODO
     * @FIXME
     * Re-work me with support of a real masks!!!
     * plz
     */
    public Comparable processExpression(LikeExpression expression) throws QueryExecutorException {
        return String.valueOf( this.processStatement( expression.getField() ) )
              .contains( 
                  String.valueOf( this.processStatement(expression.getMask()) )
              );
    }

    @Override
    public Comparable[] processStatement(IArrayStatement statement) throws QueryExecutorException {
        Comparable[] result = new Comparable[statement.getSize()];
        for ( int i = 0; i < statement.getSize(); i++ ) {
            result[i] = this.processStatement(statement.getStatement(i));
        }

        return result;
    }

    @Override
    public Boolean processExpression(NotOperation not) throws QueryExecutorException {
        return !this.processExpression(not.getExpression());
    }

}
