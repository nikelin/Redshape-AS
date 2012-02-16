package com.redshape.utils.beans.bindings.types;

import com.redshape.utils.beans.bindings.BindingException;

public interface IBindableMap extends IBindable {
	
	public String getKeyName() throws BindingException;
	
	public Class<?> getKeyType() throws BindingException;
	
	public String getValueName() throws BindingException;
	
	public Class<?> getValueType() throws BindingException;
	
}
