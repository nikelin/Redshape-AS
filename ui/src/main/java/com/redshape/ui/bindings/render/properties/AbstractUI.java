package com.redshape.ui.bindings.render.properties;

import javax.swing.JComponent;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.Dispatcher;
import com.redshape.ui.bindings.properties.IPropertyUI;
import com.redshape.ui.events.UIEvents;

public abstract class AbstractUI<D extends JComponent, E extends JComponent, T> 
							implements IPropertyUI<D, E, T> {
	protected D display;
	protected E editor;
	private T value;
	private IBindable descriptor;
	
	public AbstractUI( IBindable descriptor ) {
		super();
		
		this.descriptor = descriptor;
	}
	
	protected IBindable getDescriptor() {
		return this.descriptor;
	}
	
	protected void updateUI() {
		if ( this.editor != null ) {
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.editor );
		}
		
		if ( this.display != null ) {
			Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.display );
		}
	}
	
	abstract protected D createDisplay();
	
	abstract protected E createEditor();
	
	abstract protected void updateValue();
	
	@Override
	public D renderDisplay() {
		if ( this.display == null ) {
			this.display = this.createDisplay();
		}
		
		return this.display;
	}

	@Override
	public E renderEditor() {
		if ( this.editor == null ) {
			this.editor = this.createEditor();
		}
		
		return this.editor;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
		this.updateValue();
		this.updateUI();
	}

	@Override
	public T getValue() {
		return this.value;
	}

}
