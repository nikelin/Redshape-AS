package com.redshape.ui.data.bindings.render.properties;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.properties.IPropertyUI;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.EnumSet;

public class EnumUI extends JComboBox implements IPropertyUI<Enum<?>, JComboBox> {
	private static final long serialVersionUID = -3793500011500599518L;
	
	private IBindable descriptor;

	public EnumUI( IBindable bindable ) {
		this.descriptor = bindable;

        this.init();
	}

    @Override
    public JComboBox asComponent() {
        return this;
    }

	@SuppressWarnings("unchecked")
	protected void init() {
		@SuppressWarnings("rawtypes")
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
