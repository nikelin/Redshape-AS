package com.redshape.ui.bindings.views;

import com.redshape.bindings.BindingException;
import com.redshape.bindings.types.IBindable;

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
