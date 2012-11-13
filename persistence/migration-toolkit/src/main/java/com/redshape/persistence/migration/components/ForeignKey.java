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
public class ForeignKey {
    private String name;
    private Set<String> localKeys = new HashSet<String>();
    private Set<String> foreignKeys = new HashSet<String>();
    private String foreignTable;
    private ReferenceOption referenceOption;

    public ForeignKey() { }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addLocalKey( String key ) {
        this.localKeys.add(key);
    }

    public Set<String> getLocalKeys() {
        return this.localKeys;
    }

    public void addForeignKey( String key ) {
        this.foreignKeys.add(key);
    }

    public Set<String> getForeignKeys() {
        return this.foreignKeys;
    }

    public void setForeignTable( String tblName ) {
        this.foreignTable = tblName;
    }

    public String getForeignTable() {
        return this.foreignTable;
    }

    public void setReferenceOption( ReferenceOption option ) {
        this.referenceOption = option;
    }

    public ReferenceOption getReferenceOption() {
        return this.referenceOption;
    }

}
