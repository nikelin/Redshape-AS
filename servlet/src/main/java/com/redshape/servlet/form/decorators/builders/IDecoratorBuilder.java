package com.redshape.servlet.form.decorators.builders;

import java.util.List;
import java.util.Map;

import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.decorators.TagDecorator;

public interface IDecoratorBuilder {

	public IDecoratorBuilder withAttribute( String name, Object value );
	
	public IDecoratorBuilder withAttributes( Map<String, Object> attributes );
	
	public IDecorator withFormFieldDecorator();
	
	public IDecorator withComposedDecorator( IDecorator... decorators );
	
	public IDecorator withComposedDecorator( List<IDecorator> decorators );
	
	public IDecorator withTagDecorator( String tagName, TagDecorator.Placement placement );
	
	public IDecorator withLegendDecorator();
	
}
