package com.redshape.ui.bindings.properties;

import java.io.Serializable;

import javax.swing.JComponent;

import com.redshape.ui.UIException;


public interface IPropertyUI<D extends JComponent, E extends JComponent, T> extends Serializable {

	public D renderDisplay() throws UIException;	
	
	public E renderEditor() throws UIException;
	
	public void setValue( T value ) throws UIException;
	
	public T getValue() throws UIException;
	
}
