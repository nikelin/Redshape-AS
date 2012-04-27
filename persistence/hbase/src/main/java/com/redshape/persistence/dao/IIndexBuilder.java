package com.redshape.persistence.dao;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.executors.IStaticQueryExecutor;
import com.redshape.persistence.entities.IEntity;

import java.util.Map;

public interface IIndexBuilder {

    public Map<String, byte[]> buildIndex(IEntity entity) throws IndexBuilderException;

    public void setSerializer(ISerializer serializer);

    public ISerializer getSerializer();

    public IStaticQueryExecutor getStaticQueryExecutor(IQuery query);

    public void setEntityClass(Class<? extends IEntity> clazz);

    public Class<? extends IEntity> getEntityClass();

}