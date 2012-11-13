/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ui.data.bindings.render.properties;

import com.redshape.utils.beans.bindings.types.IBindable;
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
