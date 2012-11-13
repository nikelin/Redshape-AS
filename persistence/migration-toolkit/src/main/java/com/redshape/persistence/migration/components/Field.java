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
public class Field {
    private String name;
    private FieldType type;
    private Set<FieldOption> options = new HashSet<FieldOption>();

    public Field() {}

    public Field( String name ) {
        this(name, null );
    }

    public Field( String name, FieldType type ) {
        this( name, type, new FieldOption[] {} );
    }

    public Field( String name, FieldType type, FieldOption[] options ) {
        this(name, type, new HashSet( Arrays.asList( options ) ) );
    }

    public Field( String name, FieldType type, Set<FieldOption> options ) {
        this.name = name;
        this.type = type;
        this.options = options;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public FieldType getType() {
        return this.type;
    }

    public void setType( FieldType type ) {
        this.type = type;
    }

    public void addOption( FieldOption option ) {
        this.options.add(option);
    }

    public Set<FieldOption> getOptions() {
        return this.options;
    }

    public void setDefaultValue( String value ) {
        this.options.add( new FieldOption( FieldOptions.DEFAULT, value ) );
    }

}
