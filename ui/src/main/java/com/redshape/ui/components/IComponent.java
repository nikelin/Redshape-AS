package com.redshape.ui.components;

import java.util.Collection;

import javax.swing.Action;
import com.redshape.ui.widgets.IWidget;

public interface IComponent extends IWidget {
	
	public boolean doRenderMenu();
	
	public void doRenderMenu( boolean value );
	
	public void addAction( Action action );
	
	public <T extends Action> Collection<T> getActions();
	
	public void removeAction( Action action );
	
}
