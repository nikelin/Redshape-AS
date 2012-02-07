package com.redshape.persistence.dao.serialization.visitors;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

import org.apache.hadoop.hbase.util.Bytes;

import java.net.URI;

public class URIVisitor extends AbstractSerializerVisitor {

    @Override
    public byte[] serialize(Object object) {
        if (object == null || object.getClass() != getEntityType())
            return null;

        URI uri = (URI) object;

        return Bytes.toBytes(uri.toString());
    }

    @Override
    public Object deserialize(byte[] value) {
        if (value == null)
            return null;

        String uri = Bytes.toString(value);

        return URI.create(uri);
    }

    @Override
    public Class<?> getEntityType() {
        return URI.class;
    }
}
