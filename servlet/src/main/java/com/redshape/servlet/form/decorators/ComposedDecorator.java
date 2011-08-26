package com.redshape.servlet.form.decorators;

import com.redshape.servlet.form.IFormItem;

import java.util.Arrays;
import java.util.List;

public class ComposedDecorator extends AbstractDecorator {
	private List<IDecorator> decorators;

	public ComposedDecorator( IDecorator[] decorators ) {
		this( Arrays.asList(decorators) );
	}
	
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

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return false;
	}
}
