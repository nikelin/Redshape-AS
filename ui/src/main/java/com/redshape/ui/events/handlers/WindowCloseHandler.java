package com.redshape.ui.events.handlers;

import javax.swing.JFrame;

import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.windows.swing.ISwingWindowsManager;

public class WindowCloseHandler implements IEventHandler {
	private JFrame window;
	
	public WindowCloseHandler( JFrame window ) {
		this.window = window;
	}
	
	@Override
	public void handle(AppEvent event) {
		UIRegistry.<ISwingWindowsManager>getWindowsManager().close( this.window );
	}
	
}
