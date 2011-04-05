package com.redshape.ui.bindings.render.properties;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.Dispatcher;
import com.redshape.ui.events.UIEvents;

public class StringUI extends AbstractUI<JLabel, JTextField, String> {

	public StringUI( IBindable bindable ) {
		super(bindable);
	}
	
	@Override
	protected void updateValue() {
		if ( this.getEditor() != null ) {
			this.getEditor().setText( this.getValue() );
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.editor );
		}
		
		if ( this.getDisplay() != null ) {
			this.getDisplay().setText( this.getValue() );
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.display );
		}
	}
	
	@Override
	protected JLabel createDisplay() {
		return new JLabel();
	}

	@Override
	protected JTextField createEditor() {
		return new JTextField();
	}
	
}
