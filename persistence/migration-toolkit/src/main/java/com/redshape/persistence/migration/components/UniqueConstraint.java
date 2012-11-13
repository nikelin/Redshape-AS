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

package com.redshape.persistence.migration.components;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.components
 * @date Apr 6, 2010
 */
public class UniqueConstraint {
    private String name;
    private IndexType type;
    private Set<String> columns = new HashSet<String>();

    public UniqueConstraint() {}

    public UniqueConstraint( String name ) {
        this(name, IndexType.BTREE);
    }

    public UniqueConstraint( String name, IndexType type ) {
        this(name, type, new HashSet() );
    }

    public UniqueConstraint( String name, IndexType type, String[] columns ) {
        this(name, type, new HashSet( Arrays.asList(columns) ) );
    }

    public UniqueConstraint( String name, IndexType type, Set<String> columns ) {
        this.name = name;
        this.type = type;
        this.columns = columns;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType( IndexType type ) {
        this.type = type;
    }

    public IndexType getType() {
        return this.type;
    }

    public void addColumn( String column ) {
        this.columns.add(column);
    }

    public void setColumns( Set<String> columns ) {
        this.columns = columns;
    }

    public Set<String> getColumns() {
        return this.columns;
    }
}
