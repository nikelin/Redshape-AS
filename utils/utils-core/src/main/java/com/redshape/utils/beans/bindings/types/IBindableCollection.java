package com.redshape.utils.beans.bindings.types;

import com.redshape.utils.beans.bindings.BindingException;

public interface IBindableCollection extends IBindable {
	
	public Class<?> getElementType() throws BindingException;
	
	public CollectionType getCollectionType() throws BindingException ;
	
	public void setCollectionType( CollectionType type ) throws BindingException ;
	
}
