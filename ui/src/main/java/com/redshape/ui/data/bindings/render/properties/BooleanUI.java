package com.redshape.ui.data.bindings.render.properties;

import javax.swing.*;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.properties.IPropertyUI;

public class BooleanUI extends JCheckBox implements IPropertyUI<Boolean> {
	private static final long serialVersionUID = 7808900171495441614L;
    @SuppressWarnings("unused")
	private IBindable descriptor;

	public BooleanUI( IBindable bindable ) {
		this.descriptor = bindable;
	}

    @Override
    public JComponent asComponent() {
        return this;
    }

    @Override
    public void setValue(Boolean value) throws UIException {
        this.setSelected(value);
    }

    @Override
    public Boolean getValue() throws UIException {
        return this.isSelected();
    }
	
}
