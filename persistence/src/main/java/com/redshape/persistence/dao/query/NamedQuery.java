package com.redshape.persistence.dao.query;

import java.util.Map;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.entities.IEntity;

/**
 * @author nikelin
 */
public class NamedQuery implements IQuery {
    private IQuery query;
    private String name;

    public NamedQuery( IQuery query, String name ) {
        this.query = query;
        this.name = name;
    }

    @Override
    public IQuery where( IExpression expression ) {
        return this.query.where(expression);
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T extends IEntity> Class<T> getEntityClass() {
        return (Class<T>) this.query.getEntityClass();
    }

    @Override
    public boolean hasAttribute( String name ) {
        return this.query.hasAttribute(name);
    }

    @Override
    public <T> Map<String, T> getAttributes() {
    	return this.query.getAttributes();
    }
    
    @Override
    public <T extends IExpression> T getExpression() {
        return this.query.<T>getExpression();
    }

    @Override
    public IQuery setStatic(boolean isStatic) {
        this.query.setStatic(isStatic);
        return this;
    }

    @Override
    public boolean isStatic() {
        return this.query.isStatic();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public IQuery setAttribute(String name, Object value) {
        this.query.setAttribute(name, value);
        return this;
    }

    @Override
    public <T> T getAttribute(String name) throws QueryExecutorException {
        return this.query.<T>getAttribute(name);
    }

    @Override
    public int getOffset() {
        return this.query.getOffset();
    }

    @Override
    public void setOffset( int offset ) {
        this.query.setOffset(offset);
    }

    @Override
    public int getLimit() {
        return this.query.getLimit();
    }

    @Override
    public void setLimit( int limit ) {
        this.query.setLimit(limit);
    }

}
