package com.redshape.ui.bindings.views;

import com.redshape.bindings.BindingException;
import com.redshape.bindings.types.IBindable;

public interface ICollectionModel extends IViewModel<IBindable> {

	public Class<?> getElementType() throws BindingException;
	
}
