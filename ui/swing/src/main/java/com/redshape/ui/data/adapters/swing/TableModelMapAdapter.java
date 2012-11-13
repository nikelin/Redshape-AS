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

package com.redshape.ui.data.adapters.swing;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nikelin
 * @date 27/04/11
 * @package com.redshape.ui.data.adapters.swing
 */
public class TableModelMapAdapter implements TableModel {
    private List<Map<?, ?>> values = new ArrayList<Map<?,?>>();
    private String keyName;
    private String valueName;

    public TableModelMapAdapter( Map<?, ?> adaptable ) {
        this( "Name", "Value", adaptable );
    }

    public TableModelMapAdapter( String keyName, String valueName, Map<?,?> adaptable ) {
        this.keyName = keyName;
        this.valueName = valueName;

        this.init(adaptable);
    }

    protected void init( Map<?, ?> adaptable ) {
        for ( Object key : adaptable.keySet() ) {
            Map<Object, Object> pair = new HashMap<Object, Object>();
            pair.put( key, adaptable.get(key) );
            this.values.add( pair );
        }
    }

    @Override
    public int getRowCount() {
        return this.values.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int index) {
        return index == 0 ? keyName : valueName;
    }

    @Override
    public Class<?> getColumnClass(int index) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Map<?, ?> value = this.values.get(row);

        return col == 0 ? value.keySet().iterator().next()
                        : value.values().iterator().next();
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
