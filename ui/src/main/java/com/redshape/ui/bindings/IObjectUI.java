package com.redshape.ui.bindings;

import com.redshape.bindings.BindingException;

public interface IObjectUI<T> {

	public T synthesize() throws BindingException;
	
	public <V> V asComponent();
	
}
