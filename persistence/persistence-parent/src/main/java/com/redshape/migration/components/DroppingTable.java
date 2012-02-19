package com.redshape.migration.components;

import com.redshape.utils.Commons;

import java.util.Set;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.migration.components
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
