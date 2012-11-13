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

package com.redshape.form.builders.impl;

import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.IFormProcessHandler;
import com.redshape.form.builders.AbstractFormItemBuilder;
import com.redshape.form.builders.IFormBuilder;
import com.redshape.form.impl.Form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StandardFormBuilder extends AbstractFormItemBuilder implements IFormBuilder {
	private String legend;
	private String action; 
	private String method;
	private IFormProcessHandler processHandler;
	private List<IFormItem> items = new ArrayList<IFormItem>();


	@Override
	public IFormBuilder withAction(String path) {
		this.action = path;
		return this;
	}

	@Override
	public IFormBuilder withMethod(String method) {
		this.method = method;
		return this;
	}
	
	@Override
	public IFormBuilder withField(IFormField<?> field) {
		this.items.add(field);
		return this;
	}

	@Override
	public IFormBuilder withProcessHandler(IFormProcessHandler handler) {
		this.processHandler = handler;
		return this;
	}

	@Override
	public IFormBuilder withFields(Collection<IFormField<?>> fields) {
		this.items.addAll(fields);
		return this;
	}

	@Override
	public IFormBuilder withLegend(String legend) {
		this.legend = legend;
		return this;
	}

	@Override
	public IFormBuilder withSubForm(IForm form, String name) {
		form.setName(name);
		this.items.add(form);
		return this;
	}

	@Override
	public IForm build() {
		IForm form = new Form(this.id);
		form.addProcessHandler(this.processHandler);
		form.setName( this.name );
		form.setAction( this.action );
		form.setLegend( this.legend );
		form.setMethod( this.method );
		
		for ( IFormItem item : this.items ) {
			if ( item instanceof IFormField ) {
				form.addField( (IFormField<?>) item );
			} else if ( item instanceof IForm ) {
				form.addSubForm( (IForm) item, item.getName() );
			} 
		}
		
		return form;
	}

}
