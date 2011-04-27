package com.redshape.ui.bindings.properties;

import java.io.Serializable;

import javax.swing.JComponent;

import com.redshape.ui.UIException;


public interface IPropertyUI<T> extends Serializable {

	public void setValue( T value ) throws UIException;
	
	public T getValue() throws UIException;

    public JComponent asComponent();
	
}
