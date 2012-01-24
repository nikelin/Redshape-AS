package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IStatement;
import com.redshape.persistence.entities.IEntity;

import java.util.List;
import java.util.Map;

public interface IQuery {

    public boolean hasAttribute( String name );

    public <T extends IExpression> T getExpression();

    public String getName();

    public IQuery setAttribute(String name, Object value);

    public <T> T getAttribute(String name) throws QueryExecutorException;
    
    public IQuery setAttributes( Map<String, Object> attributes );
    
    public <T> Map<String, T> getAttributes();

    public int getOffset();

    public IQuery setOffset( int offset );

    public int getLimit();

    public List<IStatement> select();
    
    public IQuery select( IStatement... statements );

    public OrderDirection orderDirection();
    
    public IStatement orderField();
    
    public IQuery orderBy(IStatement field, OrderDirection direction );

    public List<IStatement> groupBy();

    public IQuery groupBy( IStatement... statements );
    
    public IQuery where( IExpression expression );

    public IQuery setLimit( int limit );

    public <T extends IEntity> Class<T> getEntityClass();

    public IEntity entity();
    
    public IQuery entity( IEntity entity );
    
    public boolean isNative();

    public boolean isUpdate();

    public boolean isStatic();

    public boolean isRemove();

    public boolean isCreate();
    
    public IQuery duplicate();

}

