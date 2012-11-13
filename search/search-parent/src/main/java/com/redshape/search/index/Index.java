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

package com.redshape.search.index;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:54:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Index implements IIndex {
    private Set<IIndexField> fields = new HashSet<IIndexField>();

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public IIndexField getField( String name ) {
        for ( IIndexField field : this.getFields() ) {
            if ( field.getName().equals(name) ) {
                return field;
            }
        }

        return null;
    }

    @Override
    public void addField( IIndexField field ) {
        this.fields.add(field);
    }

    @Override
    public Set<IIndexField> getFields() {
        return this.fields;
    }

    @Override
    public boolean hasField( String name ) {
        for ( IIndexField field : this.fields ) {
            if ( field.getName().equals(name) ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IIndexField createField( String fieldName ) {
        return new IndexField( fieldName );
    }

}
