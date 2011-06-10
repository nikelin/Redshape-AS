package com.redshape.servlet.form.builders;

import java.util.Collection;

import com.redshape.servlet.form.decorators.IDecorator;

public interface IFormItemBuilder {
	
	public IFormItemBuilder withName( String name );
	
	public IFormItemBuilder withId( String id );
	
	public IFormItemBuilder withEmptyDecorators();
	
	public IFormItemBuilder withDecorator( IDecorator decorator );
	
	public IFormItemBuilder withDecorators( Collection<IDecorator> decorators );
	
	public IFormItemBuilder withAttribute( String name, Object value );
	
	public IFormBuilder asFormBuilder();
	
	public IFormFieldBuilder asFieldBuilder();
	
}
