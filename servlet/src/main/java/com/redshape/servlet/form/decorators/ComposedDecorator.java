package com.redshape.servlet.form.decorators;

import java.util.List;

import com.redshape.servlet.form.IFormItem;

public class ComposedDecorator extends AbstractDecorator {
	private List<IDecorator> decorators;
	
	public ComposedDecorator( List<IDecorator> decorators ) {
		this.decorators = decorators;
	}
	
	@Override
	public String decorate(IFormItem item, String data) {
		for ( IDecorator decorator : decorators ) {
			data = decorator.decorate( item, data );
		}
		
		return data;
	}
	
}
