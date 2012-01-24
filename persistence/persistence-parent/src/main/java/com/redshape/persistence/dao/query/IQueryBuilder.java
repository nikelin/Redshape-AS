package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IArrayStatement;
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

    public IExpression lessThan(IStatement left, IStatement right);

    public IExpression greaterThan(IStatement left, IStatement right);

    public IExpression equals(IStatement term1, IStatement term2);

    public IExpression sum( IStatement term1, IStatement term2 );

    public IExpression sub( IStatement term1, IStatement term2 );

    public IExpression prod( IStatement term1, IStatement term2 );

    public IExpression div( IStatement term1, IStatement term2 );

    public IExpression mod( IStatement term1, IStatement term2 );

    public IExpression negate( IStatement term1 );

    public IExpression in(IStatement source, IArrayStatement range);

    public IExpression like( IStatement source, IStatement mask );
    
    public IArrayStatement array( IStatement... statements );

    public <T> IStatement[] scalar( T... values );
    
    public IStatement nullScalar();

    public IStatement falseValue();

    public IStatement trueValue();

    public <T> IStatement scalar(T value);

    public IStatement reference(String value);

    public IQuery query(Class<? extends IEntity> clazz);

    public IQuery nativeQuery( String name );

    public IQuery updateQuery(Class<? extends IEntity> clazz);

    public IQuery removeQuery( Class<? extends IEntity> clazz );

}


