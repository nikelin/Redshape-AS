package com.redshape.servlet.form.builders.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redshape.servlet.form.IForm;
import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.IFormItem;
import com.redshape.servlet.form.IFormProcessHandler;
import com.redshape.servlet.form.builders.IFormBuilder;
import com.redshape.servlet.form.builders.IFormFieldBuilder;
import com.redshape.servlet.form.builders.IFormItemBuilder;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.impl.Form;
import com.redshape.servlet.form.impl.internal.SubFormItem;
import com.redshape.servlet.form.render.impl.StandardFormRenderer;

public class StandardFormBuilder implements IFormBuilder {
	private String id;
	private String legend;
	private String action; 
	private String method;
	private String name;
	private IFormProcessHandler processHandler;
	private List<IFormItem> items = new ArrayList<IFormItem>();
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private Collection<IDecorator> decorators = new ArrayList<IDecorator>();
	
	@Override
	public IFormBuilder withAction(String path) {
		this.action = path;
		return this;
	}
	
	@Override
	public IFormItemBuilder withId( String id ) {
		this.id = id;
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
	public IFormItemBuilder withName( String name ) {
		this.name = name;
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
		this.items.add( new SubFormItem(form, name) );
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
			} else if ( item instanceof SubFormItem ) {
				form.addSubForm( ((SubFormItem) item).getForm(), ((SubFormItem) item).getName() );
			} 
		}
		
		return form;
	}

	@Override
	public IFormItemBuilder withEmptyDecorators() {
		this.decorators.clear();
		return this;
	}
	
	@Override
	public IFormItemBuilder withDecorator(IDecorator decorator) {
		this.decorators.add( decorator );
		return this;
	}

	@Override
	public IFormItemBuilder withDecorators(Collection<IDecorator> decorators) {
		this.decorators.addAll( decorators );
		return this;
	}

	@Override
	public IFormItemBuilder withAttribute(String name, Object value) {
		this.attributes.put(name, value);
		return this;
	}

	@Override
	public IFormBuilder asFormBuilder() {
		return this;
	}

	@Override
	public IFormFieldBuilder asFieldBuilder() {
		throw new UnsupportedOperationException("Operation not supported");
	}
	
}
