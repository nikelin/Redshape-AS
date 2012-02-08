package com.redshape.ui.data.bindings.views;

import com.redshape.bindings.IBeanInfo;

import java.util.ArrayList;
import java.util.List;

public class ComposedModel extends AbstractView<IBeanInfo> implements IComposedModel {
	private List<IViewModel<?>> children = new ArrayList<IViewModel<?>>();
	
	public ComposedModel( IBeanInfo type ) {
		super(type);
	}
	
	@Override
	public void addChild(IViewModel<?> model) {
		this.children.add(model);
	}

	@Override
	public List<IViewModel<?>> getChilds() {
		return this.children;
	}
	
}
