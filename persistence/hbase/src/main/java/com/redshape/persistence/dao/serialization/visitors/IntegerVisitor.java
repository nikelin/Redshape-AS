package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

public class IntegerVisitor extends AbstractSerializerVisitor {

    @Override
    public byte[] serialize(Object object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        Integer integer = (Integer) object;

        return Bytes.toBytes(integer.intValue());
    }

    @Override
    public Object deserialize(byte[] value) {
        if (value == null)
            return null;

        return Bytes.toInt(value);
    }

    @Override
    public Class getEntityType() {
        return Integer.class;
    }
}
