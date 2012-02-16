package com.redshape.form.builders;

import java.util.Collection;

import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormProcessHandler;

public interface IFormBuilder extends IFormItemBuilder {
	
	public IFormBuilder withProcessHandler( IFormProcessHandler handler );
	
	public IFormBuilder withAction( String path );
	
	public IFormBuilder withMethod( String method );
	
	public IFormBuilder withField( IFormField<?> field );
	
	public IFormBuilder withFields( Collection<IFormField<?>> fields );
	
	public IFormBuilder withLegend( String legend );
	
	public IFormBuilder withSubForm( IForm form, String name );
	
	public IForm build();
	
}
