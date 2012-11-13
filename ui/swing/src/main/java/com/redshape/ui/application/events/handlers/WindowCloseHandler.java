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

package com.redshape.ui.application.events.handlers;

import javax.swing.JFrame;

import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.windows.ISwingWindowsManager;

public class WindowCloseHandler implements IEventHandler {
	private static final long serialVersionUID = 3264428332917520542L;
	
	private JFrame window;
	
	public WindowCloseHandler( JFrame window ) {
		this.window = window;
	}
	
	@Override
	public void handle(AppEvent event) {
		UIRegistry.<ISwingWindowsManager>getWindowsManager().close( this.window );
        this.window.setVisible(false);
	}
	
}
