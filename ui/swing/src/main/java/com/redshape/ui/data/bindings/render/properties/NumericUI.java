package com.redshape.ui.data.bindings.render.properties;

import com.redshape.utils.beans.bindings.types.IBindable;
import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.properties.IPropertyUI;
import com.redshape.utils.Commons;

import javax.swing.*;

public class NumericUI extends JTextField implements IPropertyUI<Number, JTextField> {
	private static final long serialVersionUID = -5716529017245176653L;

    @SuppressWarnings("unused")
	private IBindable descriptor;

	public NumericUI( IBindable bindable ) {
        super();

        Commons.checkNotNull(bindable);

		this.descriptor = bindable;
	}

    @Override
    public JTextField asComponent() {
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
