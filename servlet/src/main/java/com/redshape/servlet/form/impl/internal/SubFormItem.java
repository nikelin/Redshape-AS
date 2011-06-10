package com.redshape.servlet.form.impl.internal;

import java.util.List;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.form.IForm;
import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.IFormItem;
import com.redshape.servlet.form.IFormProcessHandler;
import com.redshape.servlet.form.InvalidDataException;
import com.redshape.servlet.form.impl.Form;
import com.redshape.servlet.form.render.IFormRenderer;

public class SubFormItem extends Form {
	private static final long serialVersionUID = -3380922220515950665L;
	
	private String name;
	private IForm form;
	
	public SubFormItem( IForm form, String name ) {
		super(null);
		
		this.name = name;
		this.form = form;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}

	public IForm getForm() {
		return form;
	}

	public void setForm(IForm form) {
		this.form = form;
	}

	@Override
	public void setProcessHandler(IFormProcessHandler handler) {
		this.getForm().setProcessHandler(handler);
	}

	@Override
	public void process(IHttpRequest request) throws InvalidDataException {
		this.getForm().process(request);
	}

	@Override
	public <T> IFormField<T> findField(String path) {
		return this.getForm().findField(path);
	}

	@Override
	public void setRenderer(IFormRenderer renderer) {
		this.getForm().setRenderer(renderer);
	}

	@Override
	public String render() {
		return this.getForm().render();
	}

	@Override
	public void setLegend(String legend) {
		this.getForm().setLegend(legend);
	}

	@Override
	public String getLegend() {
		return this.getForm().getLegend();
	}

	@Override
	public void setAction(String action) {
		this.getForm().setAction(action);
	}

	@Override
	public String getAction() {
		return this.getForm().getAction();
	}

	@Override
	public void setMethod(String method) {
		this.getForm().setMethod(method);
	}

	@Override
	public String getMethod() {
		return this.getForm().getMethod();
	}

	@Override
	public void addField(IFormField<?> field) {
		this.getForm().addField(field);
	}

	@Override
	public void removeField(IFormField<?> field) {
		this.getForm().removeField(field);
	}
 
	@Override
	public List<IFormField<?>> getFields() {
		return this.getForm().getFields();
	}

	@Override
	public void addSubForm(IForm form, String name) {
		this.getForm().addSubForm(form, name);
	}

	@Override
	public void removeSubForm(String name) {
		this.getForm().removeSubForm(name);
	}

	@Override
	public List<IForm> getSubForms() {
		return this.getForm().getSubForms();
	}

	@Override
	public List<IFormItem> getItems() {
		return this.getForm().getItems();
	}

	@Override
	public boolean isValid() {
		return this.getForm().isValid();
	}

	@Override
	public String toString() {
		return this.getForm().toString();
	}
}