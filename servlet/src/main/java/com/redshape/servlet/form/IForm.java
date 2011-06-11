package com.redshape.servlet.form;

import java.util.List;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.form.render.IFormRenderer;

public interface IForm extends IFormItem {
	
	public void setProcessHandler( IFormProcessHandler handler );
	
	public void process( IHttpRequest request ) throws InvalidDataException;
	
	public void setLegend( String legend );
	
	public String getLegend();
	
	public void setAction( String action );
	
	public String getAction();
	
	public void setMethod( String method );
	
	public String getMethod();
	
	public IForm findContext( String name );
	
	public <T> IFormField<T> findField( String name );
	
	public void addField( IFormField<?> field );
	
	public void removeField( IFormField<?> field );
	
	public List<IFormField<?>> getFields();
	
	public void addSubForm( IForm form, String name );
	
	public void removeSubForm( String name );
	
	public List<IForm> getSubForms();
	
	public List<IFormItem> getItems();

	public IFormRenderer getRenderer();
	
	public void setRenderer( IFormRenderer renderer );
	
}
