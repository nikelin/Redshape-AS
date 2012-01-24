package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

public class DateVisitor extends AbstractSerializerVisitor {
    @Override
    public byte[] serialize(Object object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        return Bytes.toBytes(((Date) object).getTime());
    }

    @Override
    public Object deserialize(byte[] value) {
        if (value == null)
            return null;

        long representation = Bytes.toLong(value);
        Date date = new Date(representation);
        return date;
    }

    @Override
    public Class getEntityType() {
        return Date.class;
    }
}

