package com.redshape.ui.bindings.views;

import com.redshape.bindings.BindingException;

public interface IChoiceModel extends IPropertyModel {

	public Class<?> getElementType() throws BindingException;
	
}
