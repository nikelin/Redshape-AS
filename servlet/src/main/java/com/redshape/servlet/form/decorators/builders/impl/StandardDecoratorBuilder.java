package com.redshape.servlet.form.decorators.builders.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redshape.servlet.form.decorators.ComposedDecorator;
import com.redshape.servlet.form.decorators.FormFieldDecorator;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.decorators.LegendDecorator;
import com.redshape.servlet.form.decorators.TagDecorator;
import com.redshape.servlet.form.decorators.TagDecorator.Placement;
import com.redshape.servlet.form.decorators.builders.IDecoratorBuilder;

public class StandardDecoratorBuilder implements IDecoratorBuilder {
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	@Override
	public IDecorator withLegendDecorator() {
		return new LegendDecorator();
	}

	@Override
	public IDecoratorBuilder withAttribute(String name, Object value) {
		this.attributes.put(name, value);
		return this;
	}

	@Override
	public IDecoratorBuilder withAttributes(Map<String, Object> attributes) {
		this.attributes.putAll(attributes);
		return this;
	}

	@Override
	public IDecorator withComposedDecorator(IDecorator... decorators) {
		return this.withComposedDecorator( Arrays.asList(decorators) );
	}

	@Override
	public IDecorator withComposedDecorator(List<IDecorator> decorators) {
		ComposedDecorator decorator = new ComposedDecorator(decorators);
		decorator.setAttributes( this.attributes );
		return decorator;
	}

	@Override
	public IDecorator withFormFieldDecorator() {
		FormFieldDecorator decorator = new FormFieldDecorator();
		decorator.setAttributes( this.attributes );
		return decorator;
	}
	
	@Override
	public IDecorator withTagDecorator(String tagName, Placement placement) {
		TagDecorator decorator = new TagDecorator(tagName, placement);
		decorator.setAttributes( this.attributes );
		return decorator;
	}

}
