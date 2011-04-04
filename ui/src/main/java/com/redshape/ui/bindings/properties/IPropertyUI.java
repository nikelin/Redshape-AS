package com.redshape.ui.bindings.properties;

import javax.swing.JComponent;


public interface IPropertyUI<D extends JComponent, E extends JComponent, T> {

	public D renderDisplay();	
	
	public E renderEditor();
	
	public void setValue( T value );
	
	public T getValue();
	
}
