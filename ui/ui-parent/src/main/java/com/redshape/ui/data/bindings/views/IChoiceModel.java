package com.redshape.ui.data.bindings.views;

import com.redshape.bindings.BindingException;

public interface IChoiceModel extends IPropertyModel {

	public Class<?> getElementType() throws BindingException;
	
}
