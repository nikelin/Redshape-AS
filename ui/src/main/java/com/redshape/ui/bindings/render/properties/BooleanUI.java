package com.redshape.ui.bindings.render.properties;

import javax.swing.JCheckBox;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.Dispatcher;
import com.redshape.ui.events.UIEvents;

public class BooleanUI extends AbstractUI<JCheckBox, JCheckBox, Boolean> {

	public BooleanUI( IBindable bindable ) {
		super(bindable);
	}
	
	@Override
	protected JCheckBox createDisplay() {
		return new JCheckBox((String)null, false);
	}
	
	@Override
	protected JCheckBox createEditor() {
		return new JCheckBox();
	}
	
	@Override
	protected void updateValue() {
		if ( this.display != null ) {
			this.display.setSelected( this.getValue() );
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.display );
		}
	}
	
}
