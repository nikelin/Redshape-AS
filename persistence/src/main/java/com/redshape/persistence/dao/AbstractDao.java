package com.redshape.persistence.dao;

import com.redshape.persistence.entities.IEntity;


public abstract class AbstractDao<T extends IEntity> implements IDAO<T> {
    protected Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return this.entityClass;
    }

}
