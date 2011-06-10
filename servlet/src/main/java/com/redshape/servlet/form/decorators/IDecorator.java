package com.redshape.servlet.form.decorators;

import java.util.Map;

import com.redshape.servlet.form.IFormItem;

public interface IDecorator {

	public String decorate( IFormItem item, String data );
	
	public void setAttribute( String name, Object value );
	
	public void setAttributes( Map<String, Object> attributes );
	
}
