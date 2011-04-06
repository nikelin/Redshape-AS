package com.redshape.ui.bindings.render.properties;

import javax.swing.JComboBox;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.render.IViewRenderer;
import com.redshape.ui.data.adapters.swing.ComboBoxAdapter;

public class ListUI extends AbstractListUI<JComboBox, JComboBox, Object> {
	private static final long serialVersionUID = -461201665686274716L;

	public ListUI( IViewRenderer<?> renderer, IBindable descriptor) {
		super(renderer, descriptor);
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected JComboBox createListElement() throws UIException {
		return new ComboBoxAdapter( this.getProvider() );
	}
	
	@Override
	protected JComboBox createDisplay() throws UIException {
		return this.createListElement();
	}

	@Override
	protected JComboBox createEditor() throws UIException {
		return this.createListElement();
	}

	@Override
	public Object getValue() {
		return this.editor.getSelectedItem();
	}
	
	@Override
	protected void updateValue() {
		// TODO!
	}

	
}
