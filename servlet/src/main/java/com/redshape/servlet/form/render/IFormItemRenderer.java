package com.redshape.servlet.form.render;

import com.redshape.servlet.form.IFormItem;

public interface IFormItemRenderer<T extends IFormItem> {

	public String render( T item );
	
}
