package com.redshape.ui.data.bindings.render.properties;

import javax.swing.*;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.properties.IPropertyUI;

public class NumericUI extends JTextField implements IPropertyUI<Number> {
	private static final long serialVersionUID = -5716529017245176653L;

    @SuppressWarnings("unused")
	private IBindable descriptor;

	public NumericUI( IBindable bindable ) {
		this.descriptor = bindable;
	}

    @Override
    public JComponent asComponent() {
        return this;
    }

    @Override
    public void setValue(Number value) throws UIException {
        this.setText( value.toString() );
    }

    @Override
    public Number getValue() throws UIException {
        try {
            return Integer.valueOf( this.getText() );
        } catch ( NumberFormatException e ) {
            try {
                return Long.valueOf( this.getText() );
            } catch ( NumberFormatException e1 ) {
                try {
                    return Double.valueOf( this.getText() );
                } catch ( NumberFormatException e2 ) {
                    throw new UIException();
                }
            }
        }
    }
}
