package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

public class BooleanSerializer extends AbstractSerializerVisitor {

    @Override
    public byte[] serialize(Object object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        Boolean boolObject = (Boolean) object;

        return Bytes.toBytes(boolObject.booleanValue());
    }

    @Override
    public Object deserialize(byte[] value) {
        if (value == null)
            return null;

        return new Boolean(Bytes.toBoolean(value));
    }

    @Override
    public Class getEntityType() {
        return Boolean.class;
    }
}

