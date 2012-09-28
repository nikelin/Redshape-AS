package com.redshape.ui.data.bindings.views;

import com.redshape.utils.beans.bindings.types.IBindable;

public class PropertyModel extends AbstractView<IBindable> implements IPropertyModel {

	public PropertyModel( IBindable descriptor ) {
		super(descriptor);
		
		this.setId( descriptor.getId() );
		this.setTitle( descriptor.getName() );
	}

	
}
