package com.redshape.ui.gwt.application.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.redshape.ui.application.events.IEventQueue;

public class GwtEventQueue implements IEventQueue {
	
	@Override
	public void invokeAndWait(final Runnable runnable) {
		GWT.runAsync( new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				runnable.run();
			}
			
			@Override
			public void onFailure(Throwable e) {
				GWT.log( e.getMessage(), e );
			}
		});
	}

	@Override
	public void invokeLater(final Runnable runnable) {
		GWT.runAsync( new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				runnable.run();
			}
			
			@Override
			public void onFailure(Throwable e) {
				GWT.log( e.getMessage(), e );
			}
		});
	}

}
