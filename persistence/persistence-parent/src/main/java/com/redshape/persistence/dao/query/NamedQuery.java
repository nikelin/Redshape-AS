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
import com.redshape.persistence.dao.query.statements.IAliasStatement;
import com.redshape.persistence.dao.query.statements.IJoinStatement;
import com.redshape.persistence.dao.query.statements.IStatement;

import java.util.List;
import java.util.Map;

/**
 * @author nikelin
 */
public class NamedQuery<T> implements IQuery<T> {

    private IQuery<T> query;
    private String name;

    public NamedQuery( IQuery<T> query, String name ) {
        this.query = query;
        this.name = name;
    }

    @Override
    public List<IAliasStatement> aliases() {
        return query.aliases();
    }

    @Override
    public void alias(IStatement source, String target) {
        query.alias(source, target);
    }

    @Override
    public List<IJoinStatement> joins() {
        return query.joins();
    }

    @Override
    public IQuery<T> join(IJoinStatement.JoinEntityType entityType, IJoinStatement.JoinType joinType, String name) {
        return query.join(entityType, joinType, name);
    }

    @Override
    public IQuery<T> join(IJoinStatement.JoinEntityType entityType, IJoinStatement.JoinType joinType, String name,
                       String alias ) {
        return query.join(entityType, joinType, name, alias );
    }

    @Override
    public IQuery<T> where( IExpression expression ) {
        return this.query.where(expression);
    }

    @Override
    public void setEntityClass(Class<T> entityClass) {
        this.query.setEntityClass(entityClass);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Class<T> getEntityClass() {
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
    public IQuery<T> setOffset( int offset ) {
        this.query.setOffset(offset);
        return this;
    }

    @Override
    public int getLimit() {
        return this.query.getLimit();
    }

    @Override
    public IQuery<T> setLimit( int limit ) {
        this.query.setLimit(limit);
        return this;
    }

    @Override
    public IQuery<T> setAttributes(Map<String, Object> attributes) {
        this.query.setAttributes(attributes);
        return this;
    }

    @Override
    public List<IStatement> select() {
        return this.query.select();
    }

    @Override
    public IQuery<T> select(IStatement... statements) {
        this.query.select(statements);
        return this;
    }

    @Override
    public OrderDirection orderDirection() {
        return this.query.orderDirection();
    }

    @Override
    public IStatement orderField() {
        return this.query.orderField();
    }

    @Override
    public IQuery<T> orderBy(IStatement field, OrderDirection direction) {
        this.query.orderBy(field, direction);
        return this;
    }

    @Override
    public List<IStatement> groupBy() {
        return this.query.groupBy();
    }

    @Override
    public IQuery<T> groupBy(IStatement... statements) {
        return this.query.groupBy(statements);
    }

    @Override
    public T entity() {
        return this.query.entity();
    }

    @Override
    public IQuery<T> entity(T entity) {
        this.query.entity(entity);
        return this;
    }

    @Override
    public boolean isNative() {
        return this.query.isNative();
    }

    @Override
    public IQuery<T> duplicate() {
        return this.query.duplicate();
    }

    @Override
    public boolean isUpdate() {
        return query.isUpdate();
    }

    @Override
    public boolean isCount() {
        return query.isUpdate();
    }

    @Override
    public boolean isRemove() {
        return query.isRemove();
    }

    @Override
    public boolean isCreate() {
        return query.isCreate();
    }
}
