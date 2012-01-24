package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.UUID;

public class UUIDVisitor extends AbstractSerializerVisitor {
    @Override
    public byte[] serialize(Object object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        return Bytes.toBytes(((UUID) object).toString());
    }

    @Override
    public Object deserialize(byte[] value) {
        if (value == null)
            return null;

        String representation = Bytes.toString(value);
        return UUID.fromString(representation);
    }

    @Override
    public Class getEntityType() {
        return UUID.class;
    }
}
