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
    public Class<?> getEntityType() {
        return Date.class;
    }
}

