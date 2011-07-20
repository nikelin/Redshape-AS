package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IStatement;
import com.redshape.persistence.entities.IEntity;

/**
 * User: cwiz
 * Date: 25.11.10
 * Time: 16:08
 */
public interface IQueryBuilder {

    public IExpression function( String name );

    public IExpression function( String name, IStatement... terms );

    public IExpression and(IExpression... terms);

    public IExpression or(IExpression term1, IExpression term2);

    public IExpression not(IExpression term);

    public IQuery query(Class<? extends IEntity> clazz);

    public IExpression lessThan(IStatement left, IStatement right);

    public IExpression greaterThan(IStatement left, IStatement right);

    public IExpression equals(IStatement term1, IStatement term2);

    public IExpression sum( IStatement term1, IStatement term2 );

    public IExpression sub( IStatement term1, IStatement term2 );

    public IExpression prod( IStatement term1, IStatement term2 );

    public IExpression div( IStatement term1, IStatement term2 );

    public IExpression mod( IStatement term1, IStatement term2 );

    public IExpression negate( IStatement term1 );

    public <T> IStatement scalar(T value);

    public IStatement reference(String value);
}


