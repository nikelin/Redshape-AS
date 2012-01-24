package com.redshape.persistence.dao;

import com.redshape.persistence.entities.IEntity;

public interface ISerializer {

    public byte[] serialize(Object object) throws SerializationException;

    public <T> T deserealize(byte[] data, Class<T> clazz) throws SerializationException;

    public void setEntityClass(Class<? extends IEntity> clazz);

    public Class<? extends IEntity> getEntityClass();
}