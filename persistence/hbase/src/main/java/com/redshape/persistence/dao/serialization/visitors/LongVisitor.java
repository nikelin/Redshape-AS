package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 7, 2010
 * Time: 5:32:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongVisitor extends AbstractSerializerVisitor<Long> {

    @Override
    public byte[] serialize( Long object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        return Bytes.toBytes( object.longValue() );
    }

    @Override
    public Long deserialize(byte[] value) {
        if (value == null)
            return null;

        return Bytes.toLong(value);
    }

    @Override
    public Class<?> getEntityType() {
        return Long.class;
    }
}
