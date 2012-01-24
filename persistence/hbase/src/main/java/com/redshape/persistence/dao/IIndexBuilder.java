package com.redshape.persistence.dao;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.executors.IStaticQueryExecutor;
import com.redshape.persistence.entities.IEntity;

import java.util.Map;

public interface IIndexBuilder {
    Map<String, byte[]> buildIndex(IEntity entity) throws IndexBuilderException;

    void setSerializer(ISerializer serializer);

    ISerializer getSerializer();

    IStaticQueryExecutor getStaticQueryExecutor(IQuery query);

    void setEntityClass(Class<? extends IEntity> clazz);

    Class<? extends IEntity> getEntityClass();
}