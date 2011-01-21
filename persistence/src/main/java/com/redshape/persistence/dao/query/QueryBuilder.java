package com.redshape.persistence.dao.query;

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
import com.redshape.persistence.entities.IEntity;

/**
 * User: cwiz
 * Date: 25.11.10
 * Time: 16:14
 */
public class QueryBuilder implements IQueryBuilder {
    @Override
    public IExpression and(IExpression... terms) {
        return new AndExpression(terms);
    }

    @Override
    public IExpression or(IExpression term1, IExpression term2) {
        return new OrExpression(term1, term2);
    }

    @Override
    public IExpression not(IExpression term) {
        return new NotOperation(term);
    }

    @Override
    public IQuery query( Class<? extends IEntity> clazz ) {
        return new Query(clazz);
    }

    @Override
    public IExpression lessThan(IStatement left, IStatement right) {
        return new LessThanOperation(left, right);
    }

    @Override
    public IExpression greaterThan(IStatement left, IStatement right) {
        return new GreaterThanOperation(left, right);
    }

    @Override
    public IExpression equals(IStatement term1, IStatement term2) {
        return new EqualsOperation(term1, term2);
    }

    @Override
    public <T> IStatement scalar( T value) {
        return new ScalarStatement<T>(value);
    }

    @Override
    public IStatement reference(String value) {
        return new ReferenceStatement(value);
    }
}

