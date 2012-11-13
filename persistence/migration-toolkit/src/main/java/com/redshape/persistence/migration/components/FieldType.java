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
public class FieldType {
    private FieldTypes type;
    private Integer length;
    private Integer decimalLength;
    private Set<String> options = new HashSet<String>();

    public FieldType() {}

    public FieldType( FieldTypes type ) {
        this( type, null );
    }

    public FieldType( FieldTypes type, Integer length ) {
        this( type, length, null );
    }

    public FieldType( FieldTypes type, Integer length, Integer decimalLength ) {
        this( type, length, decimalLength, new HashSet() );
    }

    public FieldType( FieldTypes type, Integer length, Integer decimalLength, String[] options ) {
        this( type, length, decimalLength, new HashSet( Arrays.asList(options) ) );
    }

    public FieldType( FieldTypes type, Integer length, Integer decimalLength, Set<String> options ) {
        this.type = type;
        this.length = length;
        this.decimalLength = decimalLength;
        this.options = options;
    }

    public void setLength( int length ) {
        this.length = length;
    }

    public Integer getLength() {
        return this.length;
    }

    public void setDecimalLength( int length ) {
        this.decimalLength = length;
    }

    public Integer getDecimalLength() {
        return this.decimalLength;
    }

    public void setType( FieldTypes type ) {
        this.type = type;
    }

    public FieldTypes getType() {
        return this.type;
    }

    public Set<String> getOptions() {
        return this.options;
    }

    public void addOption( String option ) {
        this.options.add(option);
    }
}
