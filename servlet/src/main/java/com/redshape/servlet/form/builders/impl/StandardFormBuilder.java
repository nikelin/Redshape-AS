package com.redshape.servlet.form.builders.impl;

import com.redshape.servlet.form.IForm;
import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.IFormItem;
import com.redshape.servlet.form.IFormProcessHandler;
import com.redshape.servlet.form.builders.AbstractFormItemBuilder;
import com.redshape.servlet.form.builders.IFormBuilder;
import com.redshape.servlet.form.impl.Form;
import com.redshape.servlet.form.render.impl.StandardFormRenderer;

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
		form.setRenderer( new StandardFormRenderer() );
		form.setProcessHandler( this.processHandler );
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
