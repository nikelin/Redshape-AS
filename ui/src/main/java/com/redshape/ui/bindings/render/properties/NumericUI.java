package com.redshape.ui.bindings.render.properties;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.redshape.bindings.types.IBindable;

public class NumericUI extends AbstractUI<JLabel, JTextField, Number> {
	private static final long serialVersionUID = -5716529017245176653L;

	public NumericUI( IBindable bindable ) {
		super(bindable);
	}
	
	@Override
	protected JLabel createDisplay() {
		return new JLabel();
	}

	@Override
	protected JTextField createEditor() {
		return new JTextField();
	}

	@Override
	protected void updateValue() {
		if ( this.display != null ) {
			this.display.setText( String.valueOf( this.getValue() ) );
		}
		
		if ( this.editor != null ) {
			this.editor.setText( String.valueOf( this.getValue() ) );
		}
	}

	
	
}
