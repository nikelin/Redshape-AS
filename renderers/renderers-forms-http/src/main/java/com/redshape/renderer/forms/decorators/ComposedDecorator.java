package com.redshape.renderer.forms.decorators;

import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;

import java.util.Arrays;
import java.util.List;

public class ComposedDecorator extends AbstractDecorator {
	private List<IDecorator<String>> decorators;

	public ComposedDecorator( IDecorator<String>[] decorators ) {
		this( Arrays.asList(decorators) );
	}
	
	public ComposedDecorator( List<IDecorator<String>> decorators ) {
		this.decorators = decorators;
	}
	
	@Override
	public String decorate(IFormItem item, String data) {
		for ( IDecorator<String> decorator : decorators ) {
			data = decorator.decorate( item, data );
		}
		
		return data;
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return false;
	}
}
