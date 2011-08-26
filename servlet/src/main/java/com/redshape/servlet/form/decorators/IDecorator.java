package com.redshape.servlet.form.decorators;

import com.redshape.servlet.form.IFormItem;

import java.util.Map;

public interface IDecorator {

	public String decorate( IFormItem item, String data );

	public boolean hasAttribute( DecoratorAttribute name );

	public void setAttribute( DecoratorAttribute name, Object value );
	
	public void setAttributes( Map<DecoratorAttribute, Object> attributes );

	public boolean isSupported( DecoratorAttribute attribute );
	
}
