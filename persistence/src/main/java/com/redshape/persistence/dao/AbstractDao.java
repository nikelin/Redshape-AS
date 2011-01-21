package com.redshape.persistence.dao;

import com.redshape.persistence.entities.IEntity;


public abstract class AbstractDao<T extends IEntity> implements IDAO<T> {
    protected Class<T> entityClass;

    protected Class<T> getEntityClass() {
        return this.entityClass;
    }

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


}
