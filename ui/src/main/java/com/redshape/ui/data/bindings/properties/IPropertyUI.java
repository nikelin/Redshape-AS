package com.redshape.ui.data.bindings.properties;

import java.io.Serializable;

import javax.swing.JComponent;

import com.redshape.ui.application.UIException;


public interface IPropertyUI<T> extends Serializable {

	public void setValue( T value ) throws UIException;
	
	public T getValue() throws UIException;

    public JComponent asComponent();
	
}
