package com.redshape.bindings.types;

import com.redshape.bindings.BindingException;

public interface IBindableMap extends IBindable {
	
	public String getKeyName() throws BindingException;
	
	public Class<?> getKeyType() throws BindingException;
	
	public String getValueName() throws BindingException;
	
	public Class<?> getValueType() throws BindingException;
	
}
