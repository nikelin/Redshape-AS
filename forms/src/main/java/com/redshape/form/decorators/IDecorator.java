package com.redshape.form.decorators;

import com.redshape.form.IFormItem;

import java.util.Map;

public interface IDecorator<T> {

	public T decorate( IFormItem item, T data );

	public boolean hasAttribute( DecoratorAttribute name );

	public void setAttribute( DecoratorAttribute name, Object value );
	
	public void setAttributes( Map<DecoratorAttribute, Object> attributes );

	public boolean isSupported( DecoratorAttribute attribute );
	
}
