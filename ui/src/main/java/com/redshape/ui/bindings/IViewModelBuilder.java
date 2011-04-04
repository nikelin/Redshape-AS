package com.redshape.ui.bindings;

import com.redshape.ui.UIException;
import com.redshape.ui.bindings.views.IComposedModel;

public interface IViewModelBuilder {

	public boolean isProcessed( Class<?> type ) throws UIException;
	
	public IComposedModel createUI( Class<?> type ) throws UIException;
	
	public IComposedModel createUI( Class<?> type, String name ) throws UIException;
	
	public IComposedModel createUI( Class<?> type, boolean processDeffered ) throws UIException;
	
	public IComposedModel createUI( Class<?> type, String id, String name, boolean processDeffered ) throws UIException;
}
