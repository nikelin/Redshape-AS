package com.redshape.ui.bindings.render.properties;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.redshape.bindings.types.IBindable;
import com.redshape.utils.EnumUtils;
import com.redshape.utils.IEnum;

public class IEnumUI extends AbstractUI<JLabel, JComboBox, IEnum<?>> {
	private static final long serialVersionUID = 5479414921873691099L;

	public IEnumUI( IBindable bindable ) {
		super(bindable);
	}

	@Override
	protected JLabel createDisplay() {
		return new JLabel();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected JComboBox createEditor() {
		JComboBox box = new JComboBox();
		
		for ( IEnum<?> item : EnumUtils.allOf( (Class<? extends IEnum>) this.getDescriptor().getType() ) ) {
			box.addItem(item);
		}
		
		box.setRenderer( new RenderGuy() );
		
		return box;
	}

	@Override
	protected void updateValue() {
		this.getEditor().setSelectedItem( this.getValue() );
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
