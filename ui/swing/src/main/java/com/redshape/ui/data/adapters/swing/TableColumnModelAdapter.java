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

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.redshape.ui.data.IModelField;
import com.redshape.ui.data.IModelType;

public class TableColumnModelAdapter extends DefaultTableColumnModel {
	private static final long serialVersionUID = 2811986599491689310L;
	public static int DEFAULT_WIDTH = 100;

	public TableColumnModelAdapter( IModelType type ) {
		super();
		
		this.init(type);
	}
	
	protected void init( IModelType type ) {
		for ( int i = 0; i < type.nonTransientCount(); i++ ) {
			IModelField field = type.getField(i);
			if ( !field.isTransient() ) {
				this.addColumn( this.adaptField( i, field ) );
			}
		}
	}
	
	private TableColumn adaptField( int index, IModelField field ) {
		TableColumn column = new TableColumn( index, DEFAULT_WIDTH);
		column.setHeaderValue( field.getTitle() );
		
		column.setCellEditor( new DefaultCellEditor( new JTextField() ) );
		
		return column;
	}
	
}
