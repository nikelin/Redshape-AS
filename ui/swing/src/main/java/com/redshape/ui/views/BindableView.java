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

package com.redshape.ui.views;

import com.redshape.ui.application.UnhandledUIException;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.data.bindings.render.ISwingRenderer;
import com.redshape.ui.data.bindings.render.IViewRendererBuilder;
import com.redshape.ui.data.bindings.render.ViewRenderer;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;
import java.awt.*;

public class BindableView implements IView<Container> {
	private static final long serialVersionUID = -4889826635545012624L;

	private Class<?> target;
	private ISwingRenderer viewRenderer;
	private JComponent view;
	
	public BindableView( Class<?> target ) {
		this.target = target;
	}
	
	@Override
	public void handle(AppEvent event) {}

	@Override
	public void init() {
		try {
			if ( this.view != null ) {
				return;
			}
			
			this.viewRenderer = UIRegistry.<IViewRendererBuilder<ISwingRenderer>>get(
                                                        UIConstants.System.VIEW_RENDERER)
                                          .createRenderer(ViewRenderer.class);
			if ( this.viewRenderer == null ) {
				throw new UnhandledUIException("There is no rendering manager registered within global context");
			}
			
			this.view = this.viewRenderer.render(this.target);
		} catch ( Throwable e ) {
			throw new UnhandledUIException("Unable to proceed bean rendering", e);
		}
	}

	@Override
	public void unload(Container component) {
		component.remove( this.view );
	}

	public JComponent getView() {
		this.init();
		
		return this.view;
	}
	
	@Override
	public void render(Container component) {
		component.add( this.view );
	}
	
}
