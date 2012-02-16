package com.redshape.ui.data.bindings.views;

import com.redshape.utils.beans.bindings.BindingException;
import com.redshape.utils.beans.bindings.types.IBindable;

public interface ICollectionModel extends IViewModel<IBindable> {

	public Class<?> getElementType() throws BindingException;
	
}
