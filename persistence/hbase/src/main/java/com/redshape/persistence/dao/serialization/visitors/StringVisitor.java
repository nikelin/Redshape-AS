package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by IntelliJ IDEA.
 * User: cwiz
 * Date: 10.11.10
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
public class StringVisitor extends AbstractSerializerVisitor {
    @Override
    public byte[] serialize(Object object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        return Bytes.toBytes((String) object);
    }

    @Override
    public Object deserialize(byte[] value) {
        if (value == null)
            return null;

        return Bytes.toString(value);
    }

    @Override
    public Class<?> getEntityType() {
        return String.class;
    }
}


