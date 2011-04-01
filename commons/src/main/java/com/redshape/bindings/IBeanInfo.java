package com.redshape.bindings;

import java.util.Collection;

import com.redshape.bindings.types.IBindable;

public interface IBeanInfo {
	
	public Collection<IBindable> getBindables() throws BindingException;
	
	public boolean isConstructable();
	
	public <T> BeanConstructor<T> getConstructor( Class<T>[] args );
	
}
