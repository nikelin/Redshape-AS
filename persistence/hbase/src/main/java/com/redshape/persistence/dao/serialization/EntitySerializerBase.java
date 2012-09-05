package com.redshape.persistence.dao.serialization;

import com.redshape.persistence.dao.ISerializer;
import com.redshape.persistence.entities.IEntity;

import java.util.ArrayList;

/**
 * User: cwiz
 * Date: 10.11.10
 * Time: 16:33
 */
public abstract class EntitySerializerBase {

    protected ArrayList<AbstractSerializerVisitor> visitors = new ArrayList<AbstractSerializerVisitor>();
    protected Class<? extends IEntity> entityClass;
    protected ISerializer serializer;

    public void addVisitor(AbstractSerializerVisitor vistor) {
        visitors.add(vistor);
    }

    protected AbstractSerializerVisitor[] getVisitor() {
        return visitors.toArray(new AbstractSerializerVisitor[visitors.size()]);
    }

    public Class<? extends IEntity> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<? extends IEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public void setSerializationWrapper(ISerializer serializer) {
        this.serializer = serializer;
    }
}
