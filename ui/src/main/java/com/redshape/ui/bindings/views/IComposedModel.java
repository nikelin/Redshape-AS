package com.redshape.ui.bindings.views;

import java.util.List;

import com.redshape.bindings.IBeanInfo;

public interface IComposedModel extends IViewModel<IBeanInfo> {
	
	public void addChild( IViewModel<?> model );
	
	public List<IViewModel<?>> getChilds();
	
}
