package com.redshape.ui.data.bindings.views;

import com.redshape.utils.beans.bindings.BindingException;
import com.redshape.utils.beans.bindings.types.IBindable;

public class CollectionModel extends AbstractView<IBindable> implements ICollectionModel {

	public CollectionModel(IBindable descriptor) {
		super(descriptor);
	}

	@Override
	public Class<?> getElementType() throws BindingException {
		return this.getDescriptor().asCollectionObject().getElementType();
	}
	
}
