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

    public <T> IArrayStatement scalarArray( T... values );

    public <T> IStatement[] scalar( T... values );
    
    public IStatement nullScalar();

    public IStatement falseValue();

    public IStatement trueValue();

    public <T> IStatement scalar(T value);

    public IStatement reference(String value);

    public <T extends IEntity>  com.redshape.persistence.dao.query.IQuery<T> query(Class<T> clazz);

    public <T extends IEntity>  com.redshape.persistence.dao.query.IQuery<T> nativeQuery( String name );

    public <T extends IEntity>  com.redshape.persistence.dao.query.IQuery<T> staticQuery( Class<T> clazz );
    
    public <T extends IEntity>  com.redshape.persistence.dao.query.IQuery<T> updateQuery( Class<T> clazz );
    
    public <T extends IEntity>  com.redshape.persistence.dao.query.IQuery<T> removeQuery( Class<T> clazz );
    
    public <T extends IEntity> com.redshape.persistence.dao.query.IQuery<T> countQuery( Class<T> clazz );

}


