package com.redshape.persistence.dao.query;

import java.util.HashMap;
import java.util.Map;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.entities.IEntity;

class Query implements IQuery {
    private IExpression expression;
    private boolean isStatic;
    private String name;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private int offset = -1;
    private int limit = -1;
    private Class<? extends IEntity> entityClass;

    public Query( Class<? extends IEntity> entityClass ) {
        this.entityClass = entityClass;
    }

    public IQuery where( IExpression expression ) {
        this.expression = expression;
        return this;
    }

    @Override
    public void setOffset( int offset ) {
        this.offset = offset;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public int getLimit() {
        return this.limit;
    }

    @Override
    public void setLimit( int limit ) {
        this.limit = limit;
    }

    @Override
    public boolean hasAttribute( String name ) {
        return this.attributes.containsKey(name);
    }

    @Override
    public <T extends IExpression> T getExpression() {
        return (T) this.expression;
    }

    @Override
    public IQuery setStatic(boolean isStatic) {
        this.isStatic = isStatic;
        return this;
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IQuery setAttribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    @Override
    public <T> Map<String, T> getAttributes() {
    	return (Map<String, T>) this.attributes;
    }

    @Override
    public <T> T getAttribute(String name) throws QueryExecutorException {
        if ( !this.attributes.containsKey(name)) {
            throw new QueryExecutorException("No such attribute: " + name);
        }

        return (T) this.attributes.get(name);
    }

    @Override
    public <T extends IEntity> Class<T> getEntityClass() {
        return (Class<T>) this.entityClass;
    }
}
