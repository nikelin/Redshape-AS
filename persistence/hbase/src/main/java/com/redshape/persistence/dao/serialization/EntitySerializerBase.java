/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
