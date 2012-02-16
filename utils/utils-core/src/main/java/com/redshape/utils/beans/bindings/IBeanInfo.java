package com.redshape.utils.beans.bindings;

import java.util.List;

import com.redshape.utils.beans.bindings.types.IBindable;
import com.redshape.utils.validators.result.IResultsList;

public interface IBeanInfo {

	public boolean isValid( Object instance ) throws BindingException;

	public IResultsList validate( Object instance ) throws BindingException;

	public Class<?> getType();
	
	public List<IBindable> getBindables() throws BindingException;
	
	public boolean isConstructable();
	
	public <T> BeanConstructor<T> getConstructor() throws NoSuchMethodException;
	
	public <T> BeanConstructor<T> getConstructor( Class<T>[] args ) throws NoSuchMethodException;
	
}
