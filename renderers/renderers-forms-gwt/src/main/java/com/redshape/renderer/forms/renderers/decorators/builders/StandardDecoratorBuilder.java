/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.renderer.forms.renderers.decorators.builders;

import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;
import com.redshape.form.decorators.Placement;
import com.redshape.form.decorators.builders.IDecoratorBuilder;
import com.redshape.renderer.forms.renderers.decorators.ComposedDecorator;
import com.redshape.renderer.forms.renderers.decorators.FormFieldDecorator;
import com.redshape.renderer.forms.renderers.decorators.LegendDecorator;
import com.redshape.renderer.forms.renderers.decorators.TagDecorator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardDecoratorBuilder implements IDecoratorBuilder<Widget> {
	private Map<DecoratorAttribute, Object> attributes = new HashMap<DecoratorAttribute, Object>();
	
	@Override
	public IDecorator<Widget> withLegendDecorator() {
		return new LegendDecorator();
	}

	@Override
	public IDecoratorBuilder<Widget> withAttribute(DecoratorAttribute name, Object value) {
		this.attributes.put(name, value);
		return this;
	}

	@Override
	public IDecoratorBuilder<Widget> withAttributes(Map<DecoratorAttribute, Object> attributes) {
		this.attributes.putAll(attributes);
		return this;
	}

	@Override
	public IDecorator<Widget> withComposedDecorator(IDecorator<Widget>... decorators) {
		return this.withComposedDecorator( Arrays.asList(decorators) );
	}

	@Override
	public IDecorator<Widget> withComposedDecorator(List<IDecorator<Widget>> decorators) {
		ComposedDecorator decorator = new ComposedDecorator(decorators);
		decorator.setAttributes( this.attributes );
		return decorator;
	}

	@Override
	public IDecorator<Widget> withFormFieldDecorator() {
		FormFieldDecorator decorator = new FormFieldDecorator();
		decorator.setAttributes( this.attributes );
		return decorator;
	}
	
	@Override
	public IDecorator<Widget> withTagDecorator(String tagName, Placement placement) {
		TagDecorator decorator = new TagDecorator(tagName, placement);
		decorator.setAttributes( this.attributes );
		return decorator;
	}

}
