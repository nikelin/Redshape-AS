package com.redshape.ui.bindings.renderers;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.bindings.IPropertyUI;
import com.redshape.ui.events.UIEvents;

public class StringUI implements IPropertyUI<String> {
	private String value;
	
	private JTextField editor;
	private JLabel display;
	
	protected void updateUI() {
		if ( this.editor != null ) {
			this.editor.setText( this.getValue() );
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.editor );
		}
		
		if ( this.display != null ) {
			this.display.setText( this.getValue() );
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.display );
		}
	}
	
	@Override
	public JComponent renderDisplay() {
		if ( null != this.display ) {
			return this.display;
		}
		
		this.display = new JLabel( this.getValue() );
		
		return this.display;
	}

	@Override
	public JComponent renderEditor() {
		if ( null != this.editor ) {
			return this.editor;
		}
		
		this.editor = new JTextField( this.getValue() );
		
		return this.editor;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
		this.updateUI();
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
}
