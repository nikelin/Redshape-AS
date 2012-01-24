package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.query.expressions.*;
import com.redshape.persistence.dao.query.expressions.operations.BinaryOperation;
import com.redshape.persistence.dao.query.expressions.operations.UnaryOperation;
import com.redshape.persistence.dao.query.statements.*;
import com.redshape.persistence.entities.IEntity;

/**
 * User: cwiz
 * Date: 25.11.10
 * Time: 16:14
 */
public class QueryBuilder implements IQueryBuilder {

    @Override
    public IStatement nullScalar() {
        return this.scalar( (Boolean) null );
    }

    @Override
    public IStatement falseValue() {
        return this.scalar(false);
    }

    @Override
    public IStatement trueValue() {
        return this.scalar(true);
    }

    @Override
    public IExpression sum(IStatement term1, IStatement term2) {
        return new BinaryOperation( BinaryOperation.Types.SUM, term1, term2 );
    }

    @Override
    public IExpression sub(IStatement term1, IStatement term2) {
        return new BinaryOperation( BinaryOperation.Types.SUBTRACT, term1, term2 );
    }

    @Override
    public IExpression prod(IStatement term1, IStatement term2) {
        return new BinaryOperation( BinaryOperation.Types.PROD, term1, term2 );
    }

    @Override
    public IExpression div(IStatement term1, IStatement term2) {
        return new BinaryOperation( BinaryOperation.Types.DIVIDE, term1, term2 );
    }

    @Override
    public IExpression mod(IStatement term1, IStatement term2) {
        return new BinaryOperation( BinaryOperation.Types.MOD, term1, term2 );
    }

    @Override
    public IExpression negate(IStatement term1) {
        return new UnaryOperation( UnaryOperation.Types.NEGATE, term1 );
    }

    @Override
    public IExpression function(String name) {
        return this.function(name, new IStatement[] {} );
    }

    @Override
    public IExpression function(String name, IStatement... terms) {
        return new FunctionExpression( name, terms );
    }

    @Override
    public IExpression and(IExpression... terms) {
        return new AndExpression(terms);
    }

    @Override
    public IExpression or(IExpression term1, IExpression term2) {
        return new OrExpression(term1, term2);
    }

    @Override
    public IExpression in(IStatement source, IArrayStatement range) {
        return new InExpression(source, range);
    }

    @Override
    public IExpression like(IStatement source, IStatement mask) {
        return new LikeExpression(source, mask);
    }

    @Override
    public IArrayStatement array(IStatement... statements) {
        return new ArrayStatement(statements);
    }

    @Override
    public IExpression not(IExpression term) {
        return new NotOperation(term);
    }

    @Override
    public IQuery query( Class<? extends IEntity> clazz ) {
        return Query.createSelect(clazz);
    }

    @Override
    public IQuery removeQuery(Class<? extends IEntity> clazz) {
        return Query.createRemove(clazz);
    }

    @Override
    public IQuery nativeQuery(String name) {
        return Query.createNative(name);
    }

    @Override
    public IQuery updateQuery(Class<? extends IEntity> clazz) {
        return Query.createUpdate(clazz);
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

    @Override
    public <T> IStatement[] scalar(T... values) {
        IStatement[] statements = new IStatement[values.length];
        for ( int i = 0; i < values.length; i++ ) {
            statements[i++] = this.scalar(values[i]);
        }

        return statements;
    }
}

