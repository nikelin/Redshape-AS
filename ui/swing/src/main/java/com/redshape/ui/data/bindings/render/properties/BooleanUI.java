package com.redshape.ui.data.bindings.render.properties;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.properties.IPropertyUI;

import javax.swing.*;

public class BooleanUI extends JCheckBox implements IPropertyUI<Boolean, JCheckBox> {
	private static final long serialVersionUID = 7808900171495441614L;
    @SuppressWarnings("unused")
	private IBindable descriptor;

	public BooleanUI( IBindable bindable ) {
		this.descriptor = bindable;
	}

    @Override
    public JCheckBox asComponent() {
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
