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

package com.redshape.utils.beans;

import java.lang.annotation.Annotation;
import java.util.Collection;

public abstract class Property implements Comparable<Property> {

    private final String name;
    private final Class<?> type;

    public Property(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    abstract public Class<?>[] getActualTypeArguments();

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName() + " of " + getType();
    }

    public int compareTo(Property o) {
        return name.compareTo(o.name);
    }

    public boolean isCollection() {
        return Collection.class.isAssignableFrom(this.type);
    }

    public boolean isNull( Object value ) {
        return this.get(value) == null;
    }

    public boolean isArray() {
        return this.type.isArray();
    }

    public boolean isWritable() {
        return true;
    }

    public boolean isReadable() {
        return true;
    }

    abstract public <T extends Annotation> T getAnnotation( Class<T> annotation );
    
    abstract public boolean hasAnnotation(Class<? extends Annotation> annotation);

    abstract public void set(Object object, Object value) throws Exception;

    abstract public Object get(Object object);
}