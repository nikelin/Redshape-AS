package com.redshape.renderer.forms.renderers.decorators;

import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;

import java.util.Arrays;
import java.util.List;

public class ComposedDecorator extends AbstractDecorator {
	private List<IDecorator<Widget>> decorators;

	public ComposedDecorator( IDecorator<Widget>[] decorators ) {
		this( Arrays.asList(decorators) );
	}
	
	public ComposedDecorator( List<IDecorator<Widget>> decorators ) {
		this.decorators = decorators;
	}
	
	@Override
	public Widget decorate(IFormItem item, Widget data) {
		for ( IDecorator<Widget> decorator : decorators ) {
			data = decorator.decorate( item, data );
		}
		
		return data;
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return false;
	}
}
