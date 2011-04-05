package com.redshape.ui.bindings.render.properties;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.redshape.bindings.types.IBindable;
import com.redshape.utils.EnumUtils;
import com.redshape.utils.IEnum;

public class IEnumUI extends AbstractUI<JLabel, JComboBox, IEnum<?>> {

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
		
		box.setRenderer( new ListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				return new JLabel( String.valueOf( ( (IEnum<?>) value).name() ) );
			}
		});
		
		return box;
	}

	@Override
	protected void updateValue() {
		this.editor.setSelectedItem( this.getValue() );
	}

}
