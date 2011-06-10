package com.redshape.ui.application.events.handlers;

import javax.swing.JFrame;

import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.windows.swing.ISwingWindowsManager;

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
