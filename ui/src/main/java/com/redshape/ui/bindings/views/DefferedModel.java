package com.redshape.ui.bindings.views;

import java.util.List;

import com.redshape.bindings.IBeanInfo;

public class DefferedModel extends AbstractView<IBeanInfo> implements IDefferedModel {
	 
	public DefferedModel( IBeanInfo type ) {
		super(type);
	}
	
	@Override
	public void addChild(IViewModel<?> model) {
		throw new UnsupportedOperationException("not supported in deffered type");
	}

	@Override
	public List<IViewModel<?>> getChilds() {
		throw new UnsupportedOperationException("not supported in deffered type");
	}
	
}
