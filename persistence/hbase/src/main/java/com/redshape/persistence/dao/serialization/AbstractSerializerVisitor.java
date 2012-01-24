package com.redshape.persistence.dao.serialization;

import com.redshape.persistence.dao.ISerializer;

public abstract class AbstractSerializerVisitor<T> {

    protected ISerializer serializer;

    public void setSerializationWrapper(ISerializer serializer) {
        this.serializer = serializer;
    }

    public abstract byte[] serialize(T object);

    public abstract T deserialize(byte[] value);

    public abstract Class getEntityType();
}
