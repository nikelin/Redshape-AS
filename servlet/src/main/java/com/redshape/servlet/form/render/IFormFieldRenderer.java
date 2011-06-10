package com.redshape.servlet.form.render;

import com.redshape.servlet.form.IFormField;

public interface IFormFieldRenderer<T extends IFormField<?>> extends IFormItemRenderer<T> {

	@Override
	public String render( T field );
	
}
