package com.redshape.persistence.dao.query;

import java.util.Map;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.entities.IEntity;

public interface IQuery {

    public boolean hasAttribute( String name );

    public <T extends IExpression> T getExpression();

    public IQuery setStatic(boolean isStatic);

    public boolean isStatic();

    public String getName();

    public IQuery setAttribute(String name, Object value);

    public <T> T getAttribute(String name) throws QueryExecutorException;
    
    public <T> Map<String, T> getAttributes();

    public int getOffset();

    public void setOffset( int offset );

    public int getLimit();

    public IQuery where( IExpression expression );

    public void setLimit( int limit );

    public <T extends IEntity> Class<T> getEntityClass();

}

