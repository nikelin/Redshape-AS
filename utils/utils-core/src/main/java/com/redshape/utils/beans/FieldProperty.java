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
import java.lang.reflect.Field;

public class FieldProperty extends GenericProperty {
    private final Field field;

    public FieldProperty(Field field) {
        super(field.getName(), field.getType(), field.getGenericType());
        this.field = field;
        field.setAccessible(true);
    }

    @Override
    public void set(Object object, Object value) throws Exception {
        field.set(object, value);
    }

    @Override
    public <T extends Annotation> T getAnnotation( Class<T> annotation ) {
        return this.field.<T>getAnnotation(annotation);
    }

    @Override
    public boolean hasAnnotation(Class<? extends Annotation> annotation) {
        return this.field.getAnnotation(annotation) != null;
    }

    @Override
    public Object get(Object object) {
        try {
            return field.get(object);
        } catch (Exception e) {
            throw new BeanException("Unable to access field " + field.getName() + " on object "
                    + object + " : " + e);
        }
    }

}