package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

public class DoubleVisitor extends AbstractSerializerVisitor {

    @Override
    public byte[] serialize(Object object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        return Bytes.toBytes((Double) object);
    }

    @Override
    public Object deserialize(byte[] value) {
        if (value == null)
            return null;

        return new Double(Bytes.toDouble(value));
    }

    @Override
    public Class<?> getEntityType() {
        return Double.class;
    }
}
