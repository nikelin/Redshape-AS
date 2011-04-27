package com.redshape.ui.bindings.render.properties;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.redshape.bindings.annotations.BindableAttributes;
import com.redshape.bindings.types.IBindable;
import com.redshape.ui.Dispatcher;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.properties.IPropertyUI;
import com.redshape.ui.events.UIEvents;

public class StringUI extends JTextField implements IPropertyUI<String> {
	private static final long serialVersionUID = -870134475160003776L;

    private IBindable descriptor;

	public StringUI( IBindable bindable ) {
		this.descriptor = bindable;
	}

    @Override
    public JComponent asComponent() {
        return this;
    }

    @Override
    public void setValue( String value) throws UIException {
        this.setText( value );
    }

    @Override
    public String getValue() throws UIException {
        return this.getText();
    }
}
