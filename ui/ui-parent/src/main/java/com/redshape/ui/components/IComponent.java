/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
