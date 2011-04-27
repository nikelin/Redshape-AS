package com.redshape.ui.bindings.render.properties;

import java.awt.Component;
import java.io.Serializable;
import java.util.EnumSet;

import javax.swing.*;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.properties.IPropertyUI;

public class EnumUI extends JComboBox implements IPropertyUI<Enum<?>> {
    private IBindable descriptor;

	public EnumUI( IBindable bindable ) {
		this.descriptor = bindable;

        this.init();
	}

    @Override
    public JComponent asComponent() {
        return this;
    }

	protected void init() {
		EnumSet<?> items = EnumSet.allOf( (Class<? extends Enum>) this.descriptor.getType() );
		for ( Object item : items ) {
			this.addItem(item);
		}
		
		this.setRenderer(new RenderGuy());
	}

    @Override
    public void setValue(Enum<?> value) throws UIException {
        this.setSelectedItem( value );
    }

    @Override
    public Enum<?> getValue() throws UIException {
        return (Enum<?>) this.getSelectedItem();
    }

    public static class RenderGuy implements ListCellRenderer, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			return new JLabel( String.valueOf( ( (Enum<?>) value).name() ) );
		}
	}

}
