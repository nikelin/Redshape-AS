package com.redshape.persistence.dao;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;
import com.redshape.persistence.dao.serialization.EntitySerializerBase;
import com.redshape.persistence.dao.serialization.visitors.*;

/**
 * User: cwiz
 * Date: 22.11.10
 * Time: 17:19
 */
public class HBaseSerializer extends EntitySerializerBase implements ISerializer {

    public HBaseSerializer() {
        super();

        this.addVisitor(new BooleanSerializer());
        this.addVisitor(new URLSerializer());
        this.addVisitor(new DateVisitor());
        this.addVisitor(new DoubleVisitor());
        this.addVisitor(new LongVisitor() );
        this.addVisitor(new StringVisitor());
        this.addVisitor(new UUIDVisitor());
        this.addVisitor(new URIVisitor());
        this.addVisitor(new IntegerVisitor());
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if ( object == null ) {
            return null;
        }

        for (AbstractSerializerVisitor visitor : this.visitors) {
            if (visitor.getEntityType().equals( object.getClass() ) ) {
                return visitor.serialize(object);
            }
        }

        throw new SerializationException("Cannot serialize object " + object.getClass().getSimpleName());
    }

    @Override
    public Object deserealize(byte[] data, Class clazz) throws SerializationException {
        for (AbstractSerializerVisitor visitor : this.visitors) {
            if ( visitor.getEntityType().equals( clazz) ) {
                return visitor.deserialize(data);
            }
        }

        throw new SerializationException("Cannot deserialize object.");
    }
}
