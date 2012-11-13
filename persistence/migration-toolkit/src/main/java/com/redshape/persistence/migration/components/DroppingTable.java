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

import com.redshape.utils.Commons;

import java.util.Set;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.migration.components
 * @date 2/17/12 {2:52 PM}
 */
public class DroppingTable extends Table {

    private Table table;

    public DroppingTable( Table table ) {
        super();

        Commons.checkNotNull(table);

        this.table = table;
    }

    @Override
    public void setTemporary(boolean isTemporary) {
        table.setTemporary(isTemporary);
    }

    @Override
    public boolean isTemporary() {
        return table.isTemporary();
    }

    @Override
    public void setName(String name) {
        table.setName(name);
    }

    @Override
    public String getName() {
        return table.getName();
    }

    @Override
    public void addField(Field field) {
        table.addField(field);
    }

    @Override
    public void removeField(Field field) {
        table.removeField(field);
    }

    @Override
    public Set<Field> getFields() {
        return table.getFields();
    }

    @Override
    public void addForeignKey(ForeignKey key) {
        table.addForeignKey(key);
    }

    @Override
    public void addUniqueConstrain(UniqueConstraint constraint) {
        table.addUniqueConstrain(constraint);
    }

    @Override
    public Set<TableOption> getOptions() {
        return table.getOptions();
    }

    @Override
    public void addOption(TableOption option) {
        table.addOption(option);
    }
}
