package com.redshape.ui.components;

import com.redshape.ui.application.IController;
import com.redshape.ui.components.actions.ComponentAction;
import com.redshape.ui.views.widgets.IWidget;

import java.util.Collection;
import java.util.List;

public interface IComponent<T> extends IWidget<T> {
	
	public boolean doRenderMenu();
	
	public void doRenderMenu( boolean value );

	public void addController( IController controller );

	public Collection<IController> getControllers();

	public void setParent( IComponent component );
	
	public IComponent getParent();
	
	public void addChild( IComponent component );

	public List<IComponent> getChildren();
	
	public void addAction( ComponentAction action );
	
	public Collection<ComponentAction> getActions();
	
	public void removeAction( ComponentAction action );
	
}
