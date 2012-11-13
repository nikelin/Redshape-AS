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

package com.redshape.ui.data.stores.filters;

import com.redshape.ui.data.IModelData;
import com.redshape.utils.IFilter;

public class RecordPropertyFilter<T extends IModelData> implements IFilter<T> {
	private static class Operation {
		public static final int EQ = 1;
		public static final int GT = 2;
		public static final int LT = 3;
		
		private int id;
		
		public Operation( int id ) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public static final Operation EQ = new Operation(Operation.EQ);
	public static final Operation GT = new Operation(Operation.GT);
	public static final Operation LT = new Operation(Operation.LT);
	
	private String propertyName;
	private Object value;
	private Operation operation;
	
	public RecordPropertyFilter( Operation operation, String propertyName, Object value ) {
		this.operation = operation;
		this.propertyName = propertyName;
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean filter( T filterable ) {
		Object value = filterable.get( this.propertyName );
		if ( value == null ) {
			return this.value == null;
		}
		
		boolean gt = false;
		switch ( this.operation.getId() ) {
		case Operation.EQ:	
			return value.equals( this.value );
		case Operation.GT:
			gt = true;
		case Operation.LT:
			if ( !( this.value instanceof Comparable<?> ) || !( value instanceof Comparable<?> ) ) {
				throw new IllegalArgumentException("Property must implements java.lang.Comparable<?>" +
						" interface to process comparation!");
			}
			
			int result = ( (Comparable<Object>) this.value ).compareTo( (Comparable<Object>) value );
			
			if ( gt ) {
				return result == 1;
			} else {
				return result == -1;
			}
		default:
			throw new IllegalArgumentException("Unknown operation"); 
		}
	}
	
}
