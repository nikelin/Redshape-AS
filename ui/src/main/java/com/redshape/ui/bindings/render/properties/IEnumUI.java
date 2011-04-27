package com.redshape.ui.bindings.render.properties;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.*;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.properties.IPropertyUI;
import com.redshape.utils.EnumUtils;
import com.redshape.utils.IEnum;

public class IEnumUI extends JComboBox implements IPropertyUI<IEnum<?>> {
	private static final long serialVersionUID = 5479414921873691099L;

    private IBindable descriptor;

	public IEnumUI( IBindable bindable ) {
		this.descriptor = bindable;

        this.init();
	}

    @Override
    public JComponent asComponent() {
        return this;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void init() {
		for ( IEnum<?> item : EnumUtils.allOf((Class<? extends IEnum>) this.descriptor.getType()) ) {
			this.addItem(item);
		}
		
		this.setRenderer(new RenderGuy());
	}

    @Override
    public void setValue(IEnum<?> value) throws UIException {
        this.setSelectedItem( value );
    }

    @Override
    public IEnum<?> getValue() throws UIException {
        return (IEnum<?>) this.getSelectedItem();
    }

    protected static class RenderGuy implements ListCellRenderer, Serializable {
		private static final long serialVersionUID = -5743380825040772169L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			return new JLabel( String.valueOf( ( (IEnum<?>) value).name() ) );
		}
	}

}
