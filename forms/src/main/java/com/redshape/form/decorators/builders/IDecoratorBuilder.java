package com.redshape.form.decorators.builders;

import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;
import com.redshape.form.decorators.Placement;

import java.util.List;
import java.util.Map;

public interface IDecoratorBuilder<T> {

	public IDecoratorBuilder<T> withAttribute( DecoratorAttribute name, Object value );
	
	public IDecoratorBuilder<T> withAttributes( Map<DecoratorAttribute, Object> attributes );
	
	public IDecorator<T> withFormFieldDecorator();
	
	public IDecorator<T> withComposedDecorator( IDecorator<T>... decorators );
	
	public IDecorator<T> withComposedDecorator( List<IDecorator<T>> decorators );
	
	public IDecorator<T> withTagDecorator( String tagName, Placement placement );
	
	public IDecorator<T> withLegendDecorator();
	
}
