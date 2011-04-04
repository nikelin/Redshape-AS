package com.redshape.ui.bindings.views;

import com.redshape.bindings.types.IBindable;

public class PropertyModel extends AbstractView<IBindable> implements IPropertyModel {

	public PropertyModel( IBindable descriptor ) {
		super(descriptor);
		
		this.setId( descriptor.getId() );
		this.setTitle( descriptor.getName() );
	}

	
}
