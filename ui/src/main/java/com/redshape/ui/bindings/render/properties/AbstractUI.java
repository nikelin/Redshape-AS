package com.redshape.ui.bindings.render.properties;

import java.awt.Dimension;

import javax.swing.JComponent;

import com.redshape.bindings.types.IBindable;
import com.redshape.ui.Dispatcher;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.properties.IPropertyUI;
import com.redshape.ui.events.UIEvents;

public abstract class AbstractUI<D extends JComponent, E extends JComponent, T> 
							implements IPropertyUI<D, E, T> {
	private static final long serialVersionUID = -8771377848383565907L;
	
	protected D display;
	protected E editor;
	private T value;
	private IBindable descriptor;
	
	public AbstractUI( IBindable descriptor ) {
		super();
		
		this.descriptor = descriptor;
	}
	
	protected D getDisplay() {
		return this.display;
	}
	
	protected E getEditor() {
		return this.editor;
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
	
	abstract protected D createDisplay() throws UIException;
	
	abstract protected E createEditor() throws UIException;
	
	abstract protected void updateValue() throws UIException;
	
	@Override
	public D renderDisplay() throws UIException {
		if ( this.display == null ) {
			this.display = this.createDisplay();
			this.display.setPreferredSize( new Dimension( 100, 25 ) );
		}
		
		return this.display;
	}

	@Override
	public E renderEditor() throws UIException {
		if ( this.editor == null ) {
			this.editor = this.createEditor();
			this.editor.setMinimumSize( new Dimension( 100, 25 ) );
			this.editor.setPreferredSize( new Dimension( 100, 25 ) );
		}
		
		return this.editor;
	}

	@Override
	public void setValue(T value) throws UIException {
		this.value = value;
		this.updateValue();
		this.updateUI();
	}

	@Override
	public T getValue() {
		return this.value;
	}

}
