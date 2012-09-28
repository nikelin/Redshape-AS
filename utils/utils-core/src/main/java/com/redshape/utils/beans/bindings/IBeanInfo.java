package com.redshape.utils.beans.bindings;

import com.redshape.utils.beans.bindings.types.IBindable;

import java.util.List;

public interface IBeanInfo {

	public Class<?> getType();
	
	public List<IBindable> getBindables() throws BindingException;
	
	public boolean isConstructable();
	
	public <T> BeanConstructor<T> getConstructor() throws NoSuchMethodException;
	
	public <T> BeanConstructor<T> getConstructor( Class<T>[] args ) throws NoSuchMethodException;
	
}
