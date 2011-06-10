package com.redshape.ui.components;

import com.redshape.ui.application.IController;
import java.util.Collection;

import javax.swing.Action;

import com.redshape.ui.views.widgets.IWidget;
import java.util.List;

public interface IComponent extends IWidget {
	
	public boolean doRenderMenu();
	
	public void doRenderMenu( boolean value );

	public void addController( IController controller );

	public Collection<IController> getControllers();

	public void setParent( IComponent component );
	
	public IComponent getParent();
	
	public void addChild( IComponent component );

	public List<IComponent> getChildren();
	
	public void addAction( Action action );
	
	public <T extends Action> Collection<T> getActions();
	
	public void removeAction( Action action );
	
}
