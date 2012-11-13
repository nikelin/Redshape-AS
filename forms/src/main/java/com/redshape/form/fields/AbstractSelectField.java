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

package com.redshape.form.fields;

import com.redshape.form.IDataFormField;
import com.redshape.form.data.IFieldDataProvider;

import java.util.LinkedHashMap;
import java.util.Map;

public class AbstractSelectField<T> extends AbstractField<T> implements IDataFormField<T> {
	private static final long serialVersionUID = 7948336414439747793L;
	private Map<String, T> options = new LinkedHashMap<String, T>();
	private IFieldDataProvider dataProvider;

	public AbstractSelectField() {
		this(null);
	}
	
	public AbstractSelectField( String id ) {
		this(id, id);
	}
	
	public AbstractSelectField( String id, String name ) {
		super(id, name );
	}

	public IFieldDataProvider getDataProvider() {
		return dataProvider;
	}

	@Override
	public void setDataProvider(IFieldDataProvider<T> provider) {
		this.dataProvider = provider;
		this.invalidate();
	}

	public void addOptions( Map<String, T> values ) {
		if ( values == null ) {
			return;
		}

		this.options.putAll( values );
	}
	
	public void addOption( String name, T value ) {
		if ( name == null ) {
			throw new IllegalArgumentException("<nul>");

		}
		this.options.put( name, value );
	}

	protected void invalidate() {
		if ( this.getDataProvider() == null ) {
			return;
		}

		if ( !this.getDataProvider().invalidate() ) {
			this.removeAll();
			this.addOptions( this.getDataProvider().provideCollection() );
		}
	}

	public Map<String, T> getOptions() {
		this.invalidate();

		return this.options;
	}

	public void removeAll() {
		this.options.clear();
	}
	
	public void removeOption( String name ) {
		if ( name == null ) {
			throw new IllegalArgumentException("<null>");
		}

		this.options.remove(name);
	}
	
}
