package com.redshape.servlet.form.render;

import com.redshape.servlet.form.IFormItem;
import com.redshape.servlet.form.RenderMode;

public interface IFormItemRenderer<T extends IFormItem> {

	public String render( T item, RenderMode mode );
	
}
