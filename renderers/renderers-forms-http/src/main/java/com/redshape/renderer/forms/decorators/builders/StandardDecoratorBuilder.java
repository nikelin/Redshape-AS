package com.redshape.renderer.forms.decorators.builders;

import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;
import com.redshape.form.decorators.Placement;
import com.redshape.form.decorators.builders.IDecoratorBuilder;
import com.redshape.renderer.forms.decorators.ComposedDecorator;
import com.redshape.renderer.forms.decorators.FormFieldDecorator;
import com.redshape.renderer.forms.decorators.LegendDecorator;
import com.redshape.renderer.forms.decorators.TagDecorator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardDecoratorBuilder implements IDecoratorBuilder<String> {
	private Map<DecoratorAttribute, Object> attributes = new HashMap<DecoratorAttribute, Object>();
	
	@Override
	public IDecorator<String> withLegendDecorator() {
		return new LegendDecorator();
	}

	@Override
	public IDecoratorBuilder<String> withAttribute(DecoratorAttribute name, Object value) {
		this.attributes.put(name, value);
		return this;
	}

	@Override
	public IDecoratorBuilder<String> withAttributes(Map<DecoratorAttribute, Object> attributes) {
		this.attributes.putAll(attributes);
		return this;
	}

	@Override
	public IDecorator<String> withComposedDecorator(IDecorator<String>... decorators) {
		return this.withComposedDecorator( Arrays.asList(decorators) );
	}

	@Override
	public IDecorator<String> withComposedDecorator(List<IDecorator<String>> decorators) {
		ComposedDecorator decorator = new ComposedDecorator(decorators);
		decorator.setAttributes( this.attributes );
		return decorator;
	}

	@Override
	public IDecorator<String> withFormFieldDecorator() {
		FormFieldDecorator decorator = new FormFieldDecorator();
		decorator.setAttributes( this.attributes );
		return decorator;
	}
	
	@Override
	public IDecorator<String> withTagDecorator(String tagName, Placement placement) {
		TagDecorator decorator = new TagDecorator(tagName, placement);
		decorator.setAttributes( this.attributes );
		return decorator;
	}

}
