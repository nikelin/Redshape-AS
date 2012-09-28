package com.redshape.ui.data.bindings.views;

import com.redshape.utils.beans.bindings.BindingException;
import com.redshape.utils.beans.bindings.types.IBindable;

public class ChoiceModel extends AbstractView<IBindable> implements IChoiceModel {

	public ChoiceModel(IBindable descriptor) {
		super(descriptor);
		
		this.setTitle( descriptor.getName() );
		this.setId( descriptor.getId() );
	}

	@Override
	public Class<?> getElementType() throws BindingException {
		return this.getDescriptor().asCollectionObject().getElementType();
	}

}
