package com.redshape.bindings.types;

import com.redshape.bindings.BindingException;

public interface IBindableCollection extends IBindable {
	
	public Class<?> getElementType() throws BindingException;
	
	public CollectionType getCollectionType() throws BindingException ;
	
	public void setCollectionType( CollectionType type ) throws BindingException ;
	
}
