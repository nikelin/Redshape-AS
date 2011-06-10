package com.redshape.ui.data.bindings;

import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.views.IComposedModel;

public interface IViewModelBuilder {

	public boolean isProcessed( Class<?> type ) throws UIException;
	
	public IComposedModel createUI( Class<?> type ) throws UIException;
	
	public IComposedModel createUI( Class<?> type, String name ) throws UIException;
	
	public IComposedModel createUI( Class<?> type, boolean processDeffered ) throws UIException;
	
	public IComposedModel createUI( Class<?> type, String id, String name, boolean processDeffered ) throws UIException;
}
