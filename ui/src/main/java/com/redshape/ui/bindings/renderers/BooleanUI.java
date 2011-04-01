package com.redshape.ui.bindings.renderers;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.bindings.IPropertyUI;
import com.redshape.ui.events.UIEvents;

public class BooleanUI implements IPropertyUI<Boolean> {
	private Boolean value;
	
	private JCheckBox display;
	
	protected void updateUI() {
		if ( this.display != null ) {
			this.display.setSelected( this.getValue() );
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.display );
		}
	}
	
	protected JCheckBox createDisplay( boolean writable ) {
		if ( null != this.display ) {
			return this.display;
		}
		
		this.display = new JCheckBox();
		this.display.setEnabled(writable);
		
		return this.display;
	}
	
	@Override
	public JComponent renderDisplay() {
		return this.createDisplay( false );
	}

	@Override
	public JComponent renderEditor() {
		return this.createDisplay( true );
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value;
		this.updateUI();
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}
	
}
