package com.redshape.ui.bindings;

import javax.swing.JComponent;

public interface IPropertyUI<T> {

	public JComponent renderDisplay();
	
	public JComponent renderEditor();
	
	public void setValue( T value );
	
	public T getValue();
	
}
