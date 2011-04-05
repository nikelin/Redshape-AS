package com.redshape.ui.bindings.render.properties;

import java.awt.Component;
import java.util.EnumSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.redshape.bindings.types.IBindable;

public class EnumUI extends AbstractUI<JLabel, JComboBox, Enum<?>>{
	
	public EnumUI( IBindable bindable ) {
		super(bindable);
	}
	
	@Override
	protected JLabel createDisplay() {
		return new JLabel();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JComboBox createEditor() {
		JComboBox box = new JComboBox();
		
		@SuppressWarnings("rawtypes")
		EnumSet<?> items = EnumSet.allOf( (Class<? extends Enum>) this.getDescriptor().getType() );
		for ( Object item : items ) {
			box.addItem( item );
		}
		
		box.setRenderer( new ListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value,
					int index, boolean isSelected, boolean cellHasFocus) {
				return new JLabel( String.valueOf( ( (Enum<?>) value).name() ) );
			}
		});
		
		return box;
	}

	@Override
	protected void updateValue() {
		this.editor.setSelectedItem( this.getValue() );
	}

}
