package com.redshape.ui.data.bindings.properties;

import com.redshape.ui.application.UIException;

import java.io.Serializable;


public interface IPropertyUI<T, V> extends Serializable {

	public void setValue( T value ) throws UIException;
	
	public T getValue() throws UIException;

    public V asComponent();
	
}
