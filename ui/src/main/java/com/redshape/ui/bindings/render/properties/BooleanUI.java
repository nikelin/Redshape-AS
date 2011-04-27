package com.redshape.ui.bindings.render.properties;

import javax.swing.*;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.Dispatcher;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.properties.IPropertyUI;
import com.redshape.ui.events.UIEvents;

public class BooleanUI extends JCheckBox implements IPropertyUI<Boolean> {
	private static final long serialVersionUID = 7808900171495441614L;
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
