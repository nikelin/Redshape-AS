package com.redshape.form;

import java.util.List;

public interface IForm extends IFormItem {

	public void copy( IForm form );

	public <T> void setValue( String path, T value );

	public <T> List<T> getValues( String name );

	public <T> T getValue( String name );

	public void setProcessHandler( IFormProcessHandler handler );
	
	public void process( IUserRequest request ) throws InvalidDataException;
	
	public void setLegend( String legend );

	public boolean hasValue( String path );

	public String getLegend();
	
	public void setAction( String action );
	
	public String getAction();
	
	public void setMethod( String method );
	
	public String getMethod();
	
	public <T extends IForm> T findContext( String name );
	
	public <T, V extends IFormField<T>> V findField( String name );
	
	public void addField( IFormField<?> field );

	public void removeField( String path );

	public void removeContext( String path );

	public void remove();

	public void removeField( IFormField<?> field );
	
	public List<IFormField<?>> getFields();
	
	public void addSubForm( IForm form, String name );
	
	public void removeSubForm( String name );
	
	public List<IForm> getSubForms();

	public List<IFormItem> getItems();
	
}
