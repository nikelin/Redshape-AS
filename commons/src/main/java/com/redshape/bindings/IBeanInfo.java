package com.redshape.bindings;

import java.util.List;

import com.redshape.bindings.types.IBindable;

public interface IBeanInfo {
	
	public Class<?> getType();
	
	public List<IBindable> getBindables() throws BindingException;
	
	public boolean isConstructable();
	
	public <T> BeanConstructor<T> getConstructor() throws NoSuchMethodException;
	
	public <T> BeanConstructor<T> getConstructor( Class<T>[] args ) throws NoSuchMethodException;
	
}
